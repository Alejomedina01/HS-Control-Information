package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.domain.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.services.ClientImplService;

import javax.validation.Valid;

@Controller
@Slf4j
public class ControllerClient {

    @Autowired
    ClientImplService service ;

    @GetMapping("/allClients")
    public String listar() {
        service.list();
        return "index";
    }
    @GetMapping("/Clients")
    public String showClients(Model model){
        var clients = service.list();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/addNewClient/")
    public String addClient(){
        return "addClients";
    }

    @PostMapping("/saveClient")
    public String saveClient(@Valid Client data, Errors errors) {
        if (errors.hasErrors()){
            return "addClients";
        }
        service.save(data);
        return "redirect:/Clients";
    }

    @GetMapping("/deleteClient")
    public String deleteClient(Client client) {
        service.delete(client);
        return "redirect:/Clients";
    }

    @GetMapping("/abrirCliente/{idClient}")
    public String openClient(Client data, Model model){
        Client client = (Client) service.findById(data.getIdClient());
        var contracts = service.findBasicDataContract(data.getIdClient());
        model.addAttribute("client", client);
        model.addAttribute("contracts", contracts);
        return "specificDataClient";
    }

    @GetMapping("/editarCliente/{idClient}")
    public String editClient(Client data, Model model){
        Client client = (Client) service.findById(data.getIdClient());
        boolean isAsociated = service.findBasicDataContract(data.getIdClient()).size() > 0;
        model.addAttribute("isAsociated", isAsociated);
        model.addAttribute("client", client);
        return "modifyClient";
    }

    @PostMapping("/saveChangesClient")
    public String saveChanges(@Valid Client client, Errors errores){
        if (errores.hasErrors()){
            return "modificar";
        }
        service.save(client);
        return "redirect:/Clients";
    }
}
