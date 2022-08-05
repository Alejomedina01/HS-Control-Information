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
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@org.springframework.stereotype.Controller
public class ControllerContract {

    Logger log = LoggerFactory.getLogger(ControllerContract.class);

    @Autowired
    private ContractServiceImp contractService;

    @Autowired
    private AwsS3Service serviceAws;

    @Autowired
    private ClientImplService clientService;
    @Autowired
    private DocumentServiceImp documentService;

    private Client client;

    @PostMapping("fail_login")
    public String handleFailedLogin(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("mensaje", "x Credenciales incorrectas")
                .addFlashAttribute("clase", "danger");
        log.info("Fallo por credenciales incorrectas" + " --Fecha: "  + LocalDate.now().toString());
        return "redirect:/login?error";
    }

    @GetMapping("/login?error")
    public String login(RedirectAttributes redirectAttrs) {
        redirectAttrs.addFlashAttribute("mensaje", "Credenciales inválidas")
                .addFlashAttribute("clase", "success");
        log.info("Fallo por credenciales invalidas" + " --Fecha: "  + LocalDate.now().toString());

        return "login";
    }

    @GetMapping("/")
    public String initial(Model model, @AuthenticationPrincipal User user) {
        log.info("Ejecutando el controlador Spring MVC");
        log.info("Usuario loggeado: " + user);
        return "index";
    }

    @GetMapping("/Contracts")
    public String showContracts(Model model,String myInput) {
        if (myInput == null || myInput.isEmpty()){
        	 log.info("No hay contrato que coincida con el ingresado" + " --Fecha: "  + LocalDate.now().toString());
            return getOnePageContracts(model, 1);
        }
        var contractsSearch = contractService.findByKeyword(myInput);
        log.info("tamaño de busquedad:"+contractsSearch.size());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", contractsSearch.size());
        model.addAttribute("contracts", contractsSearch);
        log.info("Se Mostro listado de contratos opcion contratos" + " --Fecha: "  + LocalDate.now().toString());
        return "contracts";
    }
    @GetMapping("/Contracts/page/{pageNumber}")
    public String getOnePageContracts(Model model, @PathVariable("pageNumber") int currentPage) {
        MyPage<String> page = contractService.findPage(currentPage);
        int totalPages = page.getNumberPages();
        long totalItems = page.getTotalItems();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("contracts", page.getContent());
        log.info("Se Mostro listado de contratos por pagina:  "+ page + " --Fecha: "  + LocalDate.now().toString());
        return "contracts";
    }
    @GetMapping("/addNewContract/")
    public String findClienForContract(Model model,String myInput) {
        if (myInput == null || myInput.isEmpty()){
            return getOnePageClients(model, 1);
        }
        var clients = clientService.findByKeyword(myInput);
        log.info("tamaño de busquedad: "+clients.size());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", clients.size());
        model.addAttribute("clients", clients);
        log.info("Se encontro al cliente por " +myInput + " --Fecha: "  + LocalDate.now().toString());
        return "findClient";
    }
    @GetMapping("/addNewContract/page/{pageNumber}")
    public String getOnePageClients(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Client> page = clientService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Client> clients = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("clients", clients);
        log.info("Se obtuvo la pagina "+ page.getNumber() + "para agregar contrato --Fecha: "  + LocalDate.now().toString());
        return "findClient";
    }
    @GetMapping("/addContract/")
    public String addNewContract(Model model) {
        LocalDate actual = LocalDate.now();
        log.info("Se confirmo cliente para crear contrato --" + actual.toString());
        return "addContract";
    }

    @PostMapping("/addFileContract/{idContract}")
    public String addDocumentList(Contract contract, @Valid Document document, @RequestParam MultipartFile file, RedirectAttributes redirectAttrs) {
        if (documentService.findDocumentUniqName(contract.getIdContract(), document.getNameFile()) == null){
            saveDocumentStorage(contract, file, document);
            redirectAttrs.addFlashAttribute("mensaje", "✓ Documento Agregado Correctamente")
                    .addFlashAttribute("clase", "success");
            log.info("Documento" + document.getNameFile() +  " agregado Correctamente al contrato  "+ contract.getIdContract() + "para agregar contrato --Fecha: "  + LocalDate.now().toString());

        }else{
            redirectAttrs.addFlashAttribute("mensaje", "x Error al agregar documento (nombre ya existe)")
                    .addFlashAttribute("clase", "danger");
            log.info("Error al agregar un documento con el nombre" +document.getNameFile() + " --Fecha: "  + LocalDate.now().toString());
        }
        return "redirect:/abrirContrato/"+ contract.getIdContract()+"/1";
    }

    private void saveDocumentStorage(Contract contract, MultipartFile file, Document document){
        contract = (Contract) contractService.find(contract);
        String fullName = saveFileContract( file, document.getNameFile());
        document.setFullName(fullName);
        documentService.saveDocument(document);
        documentService.updateDocumentToContractId(contract.getIdContract(), document.getIdDocument());
        log.info("Se agrego un documento con el nombre " +document.getNameFile()+ " Al contrato: "+ contract.getIdContract() + " --Fecha: "  + LocalDate.now().toString());

    }

    @GetMapping("/findClient/{idClient}")
    public String selectedClient(Model model, Client client){
        this.client = clientService.findById(client.getIdClient());
        log.info(client.getIdClient());
        model.addAttribute("client", this.client);
        log.info("Se selecciono al cliente: " +client.getIdClient() + " --Fecha: "  + LocalDate.now().toString());
        return "findClient";
    }

    @PostMapping("/saveContract")
    public String saveContract(@Valid Contract contract, Errors errors, RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()) {
            log.info("Error en guardar el contrato " + contract.getIdContract()+ " Fecha de contrato"+ contract.getContractDate() + " --Fecha: "  +  LocalDate.now().toString());
            return "addContract";
        }
        if (contractService.findById(contract.getIdContract()) == null && this.client != null){
            contractService.save(contract);
            contractService.updateContractToClientId(this.client.getIdClient(), contract.getIdContract());
            redirectAttrs.addFlashAttribute("mensaje", "✓ Contrato Agregado Correctamente")
                    .addFlashAttribute("clase", "success");
            log.info("Contrato agregado correctamente " + " --Fecha: "  +  LocalDate.now().toString());

        }else{
            redirectAttrs.addFlashAttribute("mensaje", "x Error al agregar contrato (id ya existe)")
                    .addFlashAttribute("clase", "danger");
            log.info("Error en guardar el contrato " + contract.getIdContract()+ " El id ya existe"+ " --Fecha: "  +  LocalDate.now().toString());
        }
        return "redirect:/Contracts";
    }

    private String saveFileContract(MultipartFile contractFile, String nameFile) {
        if (!contractFile.isEmpty()) {
            String newFileName = System.currentTimeMillis() + nameFile + ".pdf";
            serviceAws.uploadFile(contractFile, newFileName);
            log.info("Se guuardoo el contrato: " + nameFile);
            return newFileName;
        }
        return null;
    }
    @PostMapping("/replaceFileDocument/{idContract}")
    public String replaceFileDocument(Contract contract, @Valid Document document,
                                      @RequestParam MultipartFile file, RedirectAttributes redirectAttrs){
        contract = (Contract) contractService.find(contract);
        document=documentService.findById(document.getIdDocument());
        serviceAws.deleteFile(document.getFullName());
        String fullName = saveFileContract(file, document.getNameFile());
        document.setFullName(fullName);
        serviceAws.generatePresignedUrl(document);
        documentService.saveDocument(document);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Documento Reemplazado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Documento remplazado correctamente: " + document.getNameFile()+ " En el contrato: " + contract.getIdContract()+ " --Fecha: "  +  LocalDate.now().toString());
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
        log.info("Documento del contrato : " + contract.getIdContract() + " visualizado satisfactoriamente En el contrato: " + contract.getIdContract()+ " --Fecha: "  +  LocalDate.now().toString());

        return "showContractFiles";
    }
    public void checkPresignedUrls(List<Document> documentsContract){
        for (Document document:documentsContract) {
            Calendar cal=Calendar.getInstance();

            log.info("fecha vencimiento"+document.getExpirationDate()+"  fecha actual:"+(Instant.now().toEpochMilli()-(1000*60*60))+" hora calenadario: "+cal.getTimeInMillis());
            if(document.getPresignedUrl()== null ||
                    Long.parseLong(document.getExpirationDate())< (Instant.now().toEpochMilli())-(1000*60*60)){
                    serviceAws.generatePresignedUrl(document);
                    documentService.saveDocument(document);
            }
        }
    }

    @GetMapping("/abrirContrato/{idContract}/{pageNumber}")
    public String openContract(Model model,@PathVariable("idContract") String idContract,
                               @PathVariable("pageNumber") int currentPage,
                               String myInput) {
        Contract contract = contractService.findById(idContract);
        String idClient = contractService.findClientIdFromContract(contract.getIdContract());
        Client clientContract = clientService.findById(idClient);
        MyPage<String> myPage;
        List<Document> documentsContract=null;
        if(documentService.getTotalCountDocuments()>0){
            documentsContract=documentService.findAllDocumentsOneContract(contract.getIdContract());
        }
        myPage = getStringMyPage(model, idContract, currentPage,myInput);
        model.addAttribute("totalPages", myPage.getNumberPages());
        model.addAttribute("totalItems", myPage.getTotalItems());
        model.addAttribute("employees", myPage.getContent());
        model.addAttribute("documents",documentsContract);
        model.addAttribute("contract", contract);
        model.addAttribute("totalValue", calculateTotalValue(contract.getInitialValue(),contract.getAditionalValue()));
        model.addAttribute("pendingValue", calculatePendingValue(contract.getInitialValue(),contract.getAditionalValue(),contract.getInvoicedValue()));
        model.addAttribute("client", clientContract);
        log.info("Se abrio el contrato : " + contract.getIdContract() + " --Fecha: "  +  LocalDate.now().toString());

        return "specificDataContract";
    }

    private MyPage<String> getStringMyPage(Model model, String idContract, int currentPage,String keyWord) {
        MyPage<String> myPage;
        model.addAttribute("currentPage", 1);
        if(keyWord!=null && !keyWord.isEmpty()){
            return contractService.getEmployeesByKeyWork(idContract,keyWord);
        }
        if(currentPage <1){
            return contractService.getEmployeesAsociated(idContract,1);
        }
        model.addAttribute("currentPage", currentPage);
        return contractService.getEmployeesAsociated(idContract, currentPage);
    }

    private Double calculateTotalValue(Double contractValue, Double aditionalValue){
        return (contractValue + ((aditionalValue != null)? aditionalValue : 0));
    }

    private Double calculatePendingValue(Double contractValue, Double aditionalValue, Double invoicedValue){
        return (calculateTotalValue(contractValue,aditionalValue) - ((invoicedValue != null)? invoicedValue : 0));
    }

    @GetMapping("/editar/{idContract}")
    public String editContract(Contract contract, Model model) {
        contract = (Contract) contractService.find(contract);
        boolean isAsociated = false;
        if(documentService.getTotalCountDocuments()>0) {
            isAsociated = (contractService.getEmployeesAsociated(contract.getIdContract(),1).getContent().size() > 0 || documentService.findAllDocumentsOneContract(contract.getIdContract()).size() > 0);
        }
        log.info("edicion contrato id: "+ contract.getIdContract()+"  fecha modi:" + LocalDate.now().toString());
        model.addAttribute("actual", LocalDate.now().toString());
        model.addAttribute("contrato", contract);
        model.addAttribute("totalValue", calculateTotalValue(contract.getInitialValue(),contract.getAditionalValue()));
        model.addAttribute("pendingValue", calculatePendingValue(contract.getInitialValue(),contract.getAditionalValue(),contract.getInvoicedValue()));
        model.addAttribute("isAsociated", isAsociated);
        return "modifyContract";
    }

    @PostMapping("/saveChangesContract")
    public String saveChanges(@Valid Contract contract, Errors errors, RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()) {
            return "modificar";
        }
        contractService.save(contract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Contrato Editado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Contrato editadado correctamente: " + contract.getIdContract()+ " --Fecha: "  +  LocalDate.now().toString());

        return "redirect:/Contracts";
    }

    @GetMapping("/eliminar")
    public String deleteContract(Contract contract, RedirectAttributes redirectAttrs) {
        contractService.delete(contract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Contrato Eliminado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Contrato eliminado correctamente: " + contract.getIdContract()+ " --Fecha: "  +  LocalDate.now().toString());
        return "redirect:/Contracts";
    }
    @GetMapping("/deleteFile")
    public String deleteFile(Document document, RedirectAttributes redirectAttrs) {
        long idContract=documentService.findIdContractForDocument(document.getIdDocument());
        document=documentService.findById(document.getIdDocument());
        serviceAws.deleteFile(document.getFullName());
        documentService.delete(document);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Documento Eliminado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Documento eliminado correctamente: " + document.getNameFile()+ " --Fecha: "  +  LocalDate.now().toString());
        return "redirect:/contractFiles/"+idContract;
    }
}
