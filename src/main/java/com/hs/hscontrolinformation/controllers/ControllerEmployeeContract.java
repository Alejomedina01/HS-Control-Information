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
        log.info("e - " + employees);
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
        log.info(finalContractDate + "");
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
        ecServiceImp.save(employeeContract);
        redirectAttrs.addFlashAttribute("mensaje", "✓ Empleado Asociado al Contrato")
                .addFlashAttribute("clase", "success");
        return "redirect:/abrirContrato/"+this.idContractActual;
    }

    @GetMapping("/deleteEmployee/{idEmployee}/{idContract}")
    public String deleteAsociation(@PathVariable String idEmployee, @PathVariable String idContract){
        ecServiceImp.deleteAsociation(idEmployee, idContract);
        return "redirect:/abrirContrato/"+idContract;
    }
}
