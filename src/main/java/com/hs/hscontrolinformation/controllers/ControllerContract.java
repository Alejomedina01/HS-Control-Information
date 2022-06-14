package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.services.ClientImplService;
import com.hs.hscontrolinformation.services.ContractServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;

@org.springframework.stereotype.Controller
@Slf4j
public class ControllerContract {

    @Autowired
    private ContractServiceImp contractService;

    @Autowired
    private ClientImplService clientService;

    private Client client;

    @GetMapping("/")
    public String initial(Model model){
        log.info("Ejecutando el controlador Spring MVC");
        return "index";
    }

    @GetMapping("/Contracts")
    public String showContracts(Model model){
        var contracts = contractService.findBasicDataContract();
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @GetMapping("/addNewContract/")
    public String findClienForContract(){
        return "findClient";
    }

    @GetMapping("/addContract/")
    public String addNewContract(Model model){
        LocalDate actual = LocalDate.now();
        log.info("fecha " + actual.toString());
        model.addAttribute("actual", actual);
        return "addContract";
    }

    @GetMapping("/findClient/")
    public String selectedClient(Model model, @RequestParam Long idClient, @ModelAttribute("client") Client client){
        this.client = clientService.findById(idClient);
        model.addAttribute("client", this.client);
        return "findClient";
    }

    @PostMapping("/saveContract")
    public String saveContract(@Valid Contract contract, Errors errors){
        if (errors.hasErrors()){
            return "addContract";
        }
        contractService.save(contract);
        contractService.updateContractToClientId(client.getIdClient(), contract.getIdContract());
        return "redirect:/Contracts";
    }

    @GetMapping("/abrirContrato/{idContract}")
    public String openContract(Contract contract, Model model){
        contract = (Contract) contractService.find(contract);
        Long idClient = Long.parseLong(contractService.findClientIdFromContract(contract.getIdContract()));
        Client clientContract = clientService.findById(idClient);
        model.addAttribute("contract", contract);
        model.addAttribute("client", clientContract);
        return "specificDataContract";
    }

    @GetMapping("/editar/{idContract}")
    public String editContract(Contract contract, Model model){
        contract = (Contract) contractService.find(contract);
        LocalDate actual = LocalDate.now();
        log.info("fecha " + actual.toString());
        model.addAttribute("actual", actual);
        model.addAttribute("contrato", contract);
        return "modifyContract";
    }

    @PostMapping("/saveChangesContract")
    public String saveChanges(@Valid Contract contract, Errors errores){
        if (errores.hasErrors()){
            return "modificar";
        }
        contractService.save(contract);
        return "redirect:/Contracts";
    }

    @GetMapping("/eliminar")
    public String deleteContract(Contract contract){
        contractService.delete(contract);
        return "redirect:/Contracts";
    }
}
