package com.hs.hscontrolinformation.services;

import com.hs.hscontrolinformation.dao.EmployeeContractDao;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeContractServiceImp implements ServiceTemplate<EmployeeContract> {

    @Autowired
    private EmployeeContractDao employeeContractDao;

    @Override
    public List<EmployeeContract> list() {
        return employeeContractDao.findAll();
    }

    @Override
    public void save(EmployeeContract data) {
        employeeContractDao.save(data);
    }

    @Override
    public void delete(EmployeeContract data) {
        employeeContractDao.delete(data);
    }

    @Override
    public EmployeeContract findById(Long id) {
        return employeeContractDao.findById(id).orElse(null);
    }

    @Override
    public EmployeeContract findById(String id) {
        return null;
    }

    @Transactional(readOnly = true)
    public EmployeeContract find(EmployeeContract employeeContract) {
        return employeeContractDao.findById(employeeContract.getIdEmployeeContract()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<String> getEmployeesNotAsociated(String idContract){
        return this.employeeContractDao.getEmployeesNotAsociated(idContract);
    }

    @Transactional(readOnly = false)
    public void deleteAsociation(String idEmployee, String idContract){
        this.employeeContractDao.deleteAsociation(Long.parseLong(idEmployee), idContract);
    }

    @Transactional(readOnly = true)
    public EmployeeContract findByEmpCont(String idEmployee, String idContract){
        return this.employeeContractDao.findByEmpCont(Long.parseLong(idEmployee), idContract);
    }

}
