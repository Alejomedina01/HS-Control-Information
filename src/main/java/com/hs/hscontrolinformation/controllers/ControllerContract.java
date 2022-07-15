package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.domain.Document;
import com.hs.hscontrolinformation.services.ClientImplService;
import com.hs.hscontrolinformation.services.ContractServiceImp;
import com.hs.hscontrolinformation.services.DocumentServiceImp;
import com.hs.hscontrolinformation.util.AwsS3Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
@Slf4j
public class ControllerContract {

    private static final Logger logger = LoggerFactory.getLogger(ControllerContract.class);

    @Autowired
    private ContractServiceImp contractService;

    @Autowired
    private AwsS3Service serviceAws;

    @Autowired
    private ClientImplService clientService;
    @Autowired
    private DocumentServiceImp documentService;
    private Client client;

    @GetMapping("/login?error")
    public String login(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("mensaje", "Credenciales inválidas")
                .addFlashAttribute("clase", "success");
        return "login";
    }

    @GetMapping("/")
    public String initial(Model model) {
        log.info("Ejecutando el controlador Spring MVC");
        return "index";
    }

    @GetMapping("/Contracts")
    public String showContracts(Model model) {
        var contracts = contractService.findBasicDataContract();
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @GetMapping("/addNewContract/")
    public String findClienForContract() {
        return "findClient";
    }

    @GetMapping("/addContract/")
    public String addNewContract(Model model) {
        LocalDate actual = LocalDate.now();
        log.info("fecha " + actual.toString());
        model.addAttribute("actual", actual);
        return "addContract";
    }

    @PostMapping("/addFileContract/{idContract}")
    public String addDocumentList(Contract contract, Model model, @Valid Document document,
                                  @RequestParam MultipartFile file) {
        contract = (Contract) contractService.find(contract);
        String fullName = saveFileContract( file, document.getNameFile());
        document.setFullName(fullName);
        documentService.saveDocument(document);
        documentService.updateDocumentToContractId(contract.getIdContract(), document.getIdDocument());
        return "redirect:/abrirContrato/"+contract.getIdContract();
    }

    @GetMapping("/findClient/")
    public String selectedClient(Model model, @RequestParam Long idClient, @ModelAttribute("client") Client client){
        this.client = clientService.findById(idClient);
        model.addAttribute("client", this.client);
        return "findClient";
    }

    @PostMapping("/saveContract")
    public String saveContract(@Valid Contract contract,
                               Errors errors) {
        if (errors.hasErrors()) {
            return "addContract";
        }
        contractService.save(contract);
        contractService.updateContractToClientId(client.getIdClient(), contract.getIdContract());
        return "redirect:/Contracts";
    }

    private String saveFileContract(MultipartFile contractFile, String nameFile) {
        if (!contractFile.isEmpty()) {
            String newFileName = System.currentTimeMillis() + nameFile + ".pdf";
            serviceAws.uploadFile(contractFile, newFileName);
            return newFileName;
        }
        return null;
    }
    @PostMapping("/replaceFileDocument/{idContract}")
    public String replaceFileDocument(Contract contract, @Valid Document document,
                                      @RequestParam MultipartFile file){
        contract = (Contract) contractService.find(contract);
        document=documentService.findById(document.getIdDocument());
        serviceAws.deleteFile(document.getFullName());
        String fullName = saveFileContract(file, document.getNameFile());
        document.setFullName(fullName);
        serviceAws.generatePresignedUrl(document);
        documentService.saveDocument(document);
        return "redirect:/contractFiles/"+contract.getIdContract();
    }
    @GetMapping("/contractFiles/{idContract}")
    public String visualizeContractFiles(Contract contract, Model model){
        contract = (Contract) contractService.find(contract);
        List<Document> documentsContract=null;
        if(documentService.getTotalCountDocuments()>0) {
            documentsContract = documentService.findAllDocumentsOneContract(contract.getIdContract());
            checkPresignedUrls(documentsContract);
        }
        model.addAttribute("documents",documentsContract);
        return "showContractFiles";
    }
    public void checkPresignedUrls(List<Document> documentsContract){
        for (Document document:documentsContract) {
            if(document.getPresignedUrl()== null ||
                    Long.parseLong(document.getExpirationDate())< (Instant.now().toEpochMilli())-(1000*60*60)){
                    serviceAws.generatePresignedUrl(document);
                    documentService.saveDocument(document);
            }
        }
    }
    @GetMapping("/abrirContrato/{idContract}")
    public String openContract(Contract contract, Model model) {
        contract = (Contract) contractService.find(contract);
        Long idClient = Long.parseLong(contractService.findClientIdFromContract(contract.getIdContract()));
        Client clientContract = clientService.findById(idClient);
        List<Document> documentsContract=null;
        if(documentService.getTotalCountDocuments()>0){
            documentsContract=documentService.findAllDocumentsOneContract(contract.getIdContract());
        }
        var employees = contractService.getEmployeesAsociated(contract.getIdContract());
        model.addAttribute("documents",documentsContract);
        model.addAttribute("contract", contract);
        model.addAttribute("client", clientContract);
        model.addAttribute("employees", employees);
        return "specificDataContract";
    }

    @GetMapping("/editar/{idContract}")
    public String editContract(Contract contract, Model model) {
        contract = (Contract) contractService.find(contract);
        boolean isAsociated = false;
        if(documentService.getTotalCountDocuments()>0) {
            isAsociated = (contractService.getEmployeesAsociated(contract.getIdContract()).size() > 0 || documentService.findAllDocumentsOneContract(contract.getIdContract()).size() > 0);
        }
        LocalDate actual = LocalDate.now();
        log.info("edicion contrato id:"+ contract.getIdContract()+"  fecha modi:" + actual.toString());
        model.addAttribute("actual", actual);
        model.addAttribute("contrato", contract);
        model.addAttribute("isAsociated", isAsociated);
        return "modifyContract";
    }

    public Contract findContractById(long idContract) {
        return contractService.findById(idContract);
    }

    @PostMapping("/saveChangesContract")
    public String saveChanges(@Valid Contract contract, Errors errors) {
        if (errors.hasErrors()) {
            return "modificar";
        }
        contractService.save(contract);
        return "redirect:/Contracts";
    }

    @GetMapping("/eliminar")
    public String deleteContract(Contract contract) {
        contractService.delete(contract);
        return "redirect:/Contracts";
    }
    @GetMapping("/deleteFile")
    public String deleteContract(Document document) {
        long idContract=documentService.findIdContractForDocument(document.getIdDocument());
        document=documentService.findById(document.getIdDocument());
        serviceAws.deleteFile(document.getFullName());
        documentService.delete(document);
        return "redirect:/contractFiles/"+idContract;
    }
}
