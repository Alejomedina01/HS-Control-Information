package com.hs.hscontrolinformation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.services.ClientImplService;

import javax.validation.Valid;

@Controller

public class ControllerClient {

    @Autowired
    ClientImplService service ;

    @GetMapping("/allClients")
    public String listar() {
        service.listar();
        return "index";
    }

    @PostMapping("/saveClient")
    public String saveClient(@Valid Client data, Errors errors) {
        if (errors.hasErrors()){
            return "addClients";
        }
        service.guardar(data);
        return "index";
    }

    @DeleteMapping("/deleteClient")
    public String eliminar(Client data) {
        service.eliminar(data);
        return "index";
    }

    @GetMapping("/client/{idPersona}")
    public String encontrar(Client data) {
        service.encontrar(data);
        return "index";
    }

    @GetMapping("/Clients")
    public String addNewClient(){
        return "addClients";
    }
}