package com.hs.hscontrolinformation.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/Clients")
    public String addNewClient(){
        return "addClients";
    }
}