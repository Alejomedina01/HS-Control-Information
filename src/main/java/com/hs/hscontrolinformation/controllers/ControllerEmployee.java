package com.hs.hscontrolinformation.controllers;

import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.services.EmployeeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
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
    public String saveClient(@Valid Employee data, Errors errors) {
        if (errors.hasErrors()){
            return "addEmployee";
        }
        employeeService.save(data);
        return "redirect:/Employees";
    }

    @DeleteMapping("/deleteEmployee")
    public String deleteEmp(Employee data) {
        employeeService.delete(data);
        return "redirect:/Employees";
    }
}
