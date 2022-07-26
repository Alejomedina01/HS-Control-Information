package com.hs.hscontrolinformation.controllers;


import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import com.hs.hscontrolinformation.services.EmployeeContractServiceImp;
import com.hs.hscontrolinformation.services.EmployeeServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@org.springframework.stereotype.Controller
@Slf4j
public class ControllerEmployee {

    @Autowired
    private EmployeeServiceImp employeeService;

    @GetMapping("/Employees")
    public String showEmployees(Model model){
        var employees = employeeService.list();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/addNewEmployee/")
    public String addNewEmployee(){
        return "addEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveClient(@Valid Employee data, Errors errors, RedirectAttributes redirectAttrs) {
        if (errors.hasErrors()){
            return "addEmployee";
        }
        if(employeeService.findById(data.getIdEmployee()) == null){
            employeeService.save(data);
            redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Agregado Correctamente")
                    .addFlashAttribute("clase", "success");
        }else {
            redirectAttrs.addFlashAttribute("mensaje", "x Error al agregar empleado (id ya existe)")
                    .addFlashAttribute("clase", "danger");
        }
        return "redirect:/Employees";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmp(Employee data, RedirectAttributes redirectAttrs) {
        employeeService.delete(data);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Eliminado Correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/Employees";
    }

    @GetMapping("/abrirEmpleado/{idEmployee}")
    public String openEmployee(Employee data, Model model){
        Employee employee = (Employee) employeeService.findById(data.getIdEmployee());
        var contracts = employeeService.findBasicDataContract(data.getIdEmployee());
        model.addAttribute("employee", employee);
        model.addAttribute("contracts", contracts);
        return "specificDataEmployee";
    }

    @GetMapping("/editarEmpleado/{idEmployee}")
    public String editEmployee(Employee data, Model model){
        Employee employee = (Employee) employeeService.find(data);
        boolean isAsociated = employeeService.findBasicDataContract(data.getIdEmployee()).size() > 0;
        model.addAttribute("isAsociated", isAsociated);
        model.addAttribute("empleado", employee);
        return "modifyEmployee";
    }
    @PostMapping("/saveChangesEmployee")
    public String saveChanges(@Valid Employee employee, Errors errores, RedirectAttributes redirectAttrs){
        if (errores.hasErrors()){
            return "modificar";
        }
        employeeService.save(employee);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Editado Correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/Employees";
    }

}
