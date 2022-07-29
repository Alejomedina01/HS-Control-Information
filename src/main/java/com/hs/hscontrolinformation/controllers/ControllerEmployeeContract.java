package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import com.hs.hscontrolinformation.services.ContractServiceImp;
import com.hs.hscontrolinformation.services.EmployeeContractServiceImp;
import com.hs.hscontrolinformation.services.EmployeeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Controller
@Slf4j
public class ControllerEmployeeContract {

    @Autowired
    private EmployeeServiceImp employeeService;

    @Autowired
    private EmployeeContractServiceImp ecServiceImp;

    @Autowired
    private ContractServiceImp contractServiceImp;

    private String idContractActual;

    private Long idEmployeeActual;

    /*@GetMapping("/addEmployeeToContract/{idContract}")
    public String asociateEmployeeContract(Contract contract, Model model){
        contract = (Contract) contractServiceImp.find(contract);
        model.addAttribute("contract", contract);
        var employees = ecServiceImp.findPage(contract.getIdContract());
        model.addAttribute("employees", employees);
        log.info("La asociacion de los " + employees + " con el contrato: " + contract.getIdContract() + " --Fecha: "  + LocalDate.now().toString());
        return "asociateEC";
    }*/
    @GetMapping("/addEmployeeToContract/{idContract}")
    public String findClienForContract(Model model,@PathVariable("idContract") String idContract) {
        return getOnePageClients(model,1,idContract,null);
    }
    @GetMapping("/addEmployeeToContract/page/{pageNumber}/{idContract}")
    public String getOnePageClients(Model model, @PathVariable("pageNumber") int currentPage,@PathVariable("idContract") String idContract,String myInput) {
        Contract contract = contractServiceImp.findById(idContract);
        model.addAttribute("contract", contract);
        MyPage<String> page=null;
        if (myInput == null || myInput.isEmpty()){
            page=ecServiceImp.findPage(idContract,currentPage);
            myInput="";
        }else{
            page = ecServiceImp.findByKeyword(idContract,myInput);
            currentPage=1;
        }
        model.addAttribute("keyWordSearch",myInput);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getNumberPages());
        model.addAttribute("totalItems", page.getTotalItems());
        model.addAttribute("employees", page.getContent());
        return "asociateEC";
    }

    @GetMapping("/crearAsociacion/{idContract}/{idEmployee}")
    public String createAsociation(@PathVariable String idContract, @PathVariable String idEmployee, Model model){
        idContractActual = idContract;
        idEmployeeActual = Long.valueOf(idEmployee);
        Date contractDate = contractServiceImp.findById(idContractActual).getContractDate();
        Date finalContractDate = contractServiceImp.findById(idContractActual).getReceivalDateAct();
        log.info("Se crea la asociancon con" +finalContractDate  + " --Fecha: "  + LocalDate.now().toString());
        LocalDate actualDate = LocalDate.now();
        model.addAttribute("idContract", idContract);
        model.addAttribute("idEmployee", idEmployee);
        model.addAttribute("actualDate", actualDate);
        model.addAttribute("contractDate", contractDate);
        model.addAttribute("finalContractDate", finalContractDate);
        return "creatingEC";
    }

    @PostMapping("/saveEmployeeContract")
    public String uploadAsociation(EmployeeContract employeeContract, RedirectAttributes redirectAttrs){
        employeeContract.setContract(contractServiceImp.findById(this.idContractActual));
        employeeContract.setEmployee(employeeService.findById(this.idEmployeeActual));
        log.info("contrato cliente - " + this.idContractActual + " - " + this.idEmployeeActual  + " --Fecha: "  + LocalDate.now().toString());
        ecServiceImp.save(employeeContract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Asociado al Contrato")
                .addFlashAttribute("clase", "success");
        log.info("Contrato asociado "+ employeeContract.getContract().getIdContract()  + "empleado asociado "+ employeeContract.getEmployee().getIdEmployee()+ " --Fecha: "  + LocalDate.now().toString());
        return "redirect:/abrirContrato/"+this.idContractActual;
    }

    @GetMapping("/deleteEmployee/{idEmployee}/{idContract}")
    public String deleteAsociation(@PathVariable String idEmployee, @PathVariable String idContract, RedirectAttributes redirectAttrs){
        ecServiceImp.deleteAsociation(idEmployee, idContract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Desasociado Correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/abrirContrato/"+idContract;
    }

    @GetMapping("/modifyAsociation/{idContract}/{idEmployee}")
    public String modifyAsociation(@PathVariable String idContract, @PathVariable String idEmployee, Model model){
        EmployeeContract employeeContract = ecServiceImp.findByEmpCont(idEmployee, idContract);
        idContractActual = idContract;
        idEmployeeActual = Long.valueOf(idEmployee);
        model.addAttribute("employeeContract", employeeContract);
        return "modifyContractEmployee";
    }

    @PostMapping("/saveChangesEmpCont")
    public String saveChanges(EmployeeContract employeeContract, RedirectAttributes redirectAttrs){
        employeeContract.setContract(contractServiceImp.findById(this.idContractActual));
        employeeContract.setEmployee(employeeService.findById(this.idEmployeeActual));
        ecServiceImp.deleteAsociation(String.valueOf(employeeContract.getEmployee().getIdEmployee()), employeeContract.getContract().getIdContract());
        ecServiceImp.save(employeeContract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Asociación Editada Correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/abrirContrato/"+this.idContractActual;
    }
}
