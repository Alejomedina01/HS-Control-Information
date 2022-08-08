package com.hs.hscontrolinformation.controllers;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;


import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.services.ClientImplService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class ControllerClient {

    @Autowired
    ClientImplService service ;

    @GetMapping("/Clients")
    public String showClients(Model model,String myInput){
        var clients = service.list();
        log.info("ingreso busqueda cliente: "+myInput  + " --Fecha: "  + LocalDate.now().toString()) ;
        if (myInput == null || myInput.isEmpty()){
            return getOnePage(model, 1);
        }
        clients = service.findByKeyword(myInput);
        log.info("tamaño de busquedad:"+clients.size());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1);
        model.addAttribute("totalItems", clients.size());
        model.addAttribute("clients", clients);
        log.info("Clientes mostrados satisfactoriamente " + " --Fecha: "  + LocalDate.now().toString());
        return "clients";
    }
    @GetMapping("/Clients/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Client> page = service.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Client> clients = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("clients", clients);
        return "clients";
    }
    @GetMapping("/addNewClient/")
    public String addClient(){
        log.info("Se va a crear un nuevo cliente" + " --Fecha: "  + LocalDate.now().toString());
        return "addClients";
    }

    @PostMapping("/saveClient")
    public String saveClient(@Valid Client data, Errors errors,  RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()){
            return "addClients";
        }
        if(service.findById(data.getIdClient()) == null){
            service.save(data);
            redirectAttrs.addFlashAttribute("mensaje", "✓ Cliente Agregado Correctamente")
                    .addFlashAttribute("clase", "success");
            log.info("Se agrego correctamente el cliente: "+ data.getIdClient() + " --Fecha: "  + LocalDate.now().toString());

        }else{
            redirectAttrs.addFlashAttribute("mensaje", "x Error al agregar cliente (id ya existe)")
                    .addFlashAttribute("clase", "danger");
            log.info("El cliente ya existe con el id "+ data.getIdClient() + " --Fecha: "  + LocalDate.now().toString());

        }
        service.save(data);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Cliente Agregado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Se guardo correctamente el cliente: "+ data.getIdClient() + " --Fecha: "  + LocalDate.now().toString());
        return "redirect:/Clients";
    }

    @GetMapping("/deleteClient")
    public String deleteClient(Client client, RedirectAttributes redirectAttrs) {
        service.delete(client);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Cliente Eliminado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Se elimino correctamente el cliente: "+ client.getIdClient() + " --Fecha: "  + LocalDate.now().toString());

        return "redirect:/Clients";
    }

    @GetMapping("/abrirCliente/{idClient}")
    public String openClient(Client data, Model model){
        Client client = (Client) service.findById(data.getIdClient());
        var contracts = service.findBasicDataContract(data.getIdClient());
        model.addAttribute("client", client);
        model.addAttribute("contracts", contracts);
        log.info("Se abrio correctamente el cliente: "+ data.getIdClient() + " --Fecha: "  + LocalDate.now().toString());
        return "specificDataClient";
    }

    @GetMapping("/editarCliente/{idClient}")
    public String editClient(Client data, Model model){
        Client client = (Client) service.findById(data.getIdClient());
        boolean isAsociated = service.findBasicDataContract(data.getIdClient()).size() > 0;
        model.addAttribute("isAsociated", isAsociated);
        model.addAttribute("client", client);
        log.info("Se edito correctamente el cliente: "+ data.getIdClient() + " --Fecha: "  + LocalDate.now().toString());
        return "modifyClient";
    }

    @PostMapping("/saveChangesClient")
    public String saveChanges(@Valid Client client, Errors errores, RedirectAttributes redirectAttrs){
        if (errores.hasErrors()){
            return "modificar";
        }
        service.save(client);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Cliente Editado Correctamente")
                .addFlashAttribute("clase", "success");
        log.info("Se edito correctamente el cliente: "+ client.getIdClient() + " --Fecha: "  + LocalDate.now().toString());

        return "redirect:/abrirCliente/"+client.getIdClient();
    }
}
