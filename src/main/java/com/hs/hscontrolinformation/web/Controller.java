package com.hs.hscontrolinformation.web;

import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.service.ContractServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@org.springframework.stereotype.Controller
@Slf4j
public class Controller {

    @Autowired
    private ContractServiceImp contractService;

    @GetMapping("/")
    public String initial(Model model){
        var contracts = contractService.findBasicDataContract();
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("contracts", contracts);
        return "index";
    }

    @GetMapping("/Contracts")
    public String addNewContract(){
        return "addContract";
    }

    @PostMapping("/saveContract")
    public String guardar(@Valid Contract contract, Errors errors){
        if (errors.hasErrors()){
            return "";
        }
        contractService.guardar(contract);
        return "redirect:/";
    }

}
