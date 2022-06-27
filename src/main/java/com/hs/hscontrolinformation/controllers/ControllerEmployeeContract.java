package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import com.hs.hscontrolinformation.services.ContractServiceImp;
import com.hs.hscontrolinformation.services.EmployeeContractServiceImp;
import com.hs.hscontrolinformation.services.EmployeeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@org.springframework.stereotype.Controller
@Slf4j
public class ControllerEmployeeContract {

    @Autowired
    private EmployeeServiceImp employeeService;

    @Autowired
    private EmployeeContractServiceImp ecServiceImp;

    @Autowired
    private ContractServiceImp contractServiceImp;

    private Long idContractActual;

    private Long idEmployeeActual;

    @GetMapping("/addEmployeeToContract/{idContract}")
    public String asociateEmployeeContract(Contract contract, Model model){
        contract = (Contract) contractServiceImp.find(contract);
        model.addAttribute("contract", contract);
        var employees = ecServiceImp.getEmployeesNotAsociated(contract.getIdContract());
        model.addAttribute("employees", employees);
        log.info("e - " + employees);
        return "asociateEC";
    }

    @GetMapping("/crearAsociacion/{idContract}/{idEmployee}")
    public String createAsociation(@PathVariable String idContract, @PathVariable String idEmployee, Model model){
        idContractActual = Long.valueOf(idContract);
        idEmployeeActual = Long.valueOf(idEmployee);
        model.addAttribute("idContract", idContract);
        model.addAttribute("idEmployee", idEmployee);
        return "creatingEC";
    }

    @PostMapping("/saveEmployeeContract")
    public String uploadAsociation(EmployeeContract employeeContract){
        employeeContract.setContract(contractServiceImp.findById(this.idContractActual));
        employeeContract.setEmployee(employeeService.findById(this.idEmployeeActual));
        log.info("ce - " + employeeContract);
        ecServiceImp.save(employeeContract);
        return "redirect:/Contracts";
    }


}