package com.hs.hscontrolinformation.services;

import com.hs.hscontrolinformation.dao.EmployeeDao;
import com.hs.hscontrolinformation.domain.Client;
import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class EmployeeServiceImp implements ServiceTemplate<Employee> {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> list() {
        return employeeDao.findAll();
    }

    @Override
    public void save(Employee data) {
        employeeDao.save(data);
    }

    @Override
    public void delete(Employee data) {
        employeeDao.delete(data);
    }
    @Transactional(readOnly = true)
    public List<Employee> findByKeyword(String keyword){
        return employeeDao.findAllByKeyWord("%"+keyword.toLowerCase()+"%");
    }
    @Override
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeDao.findById(id).orElse(null);
    }

    @Override
    public Employee findById(String id) {
        return null;
    }
    @Transactional(readOnly = true)
    public Page<Employee> findPage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,4);
        return employeeDao.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Employee find(Employee employee) {
        return employeeDao.findById(employee.getIdEmployee()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataContract(Long employeeId){
        return employeeDao.findContractsFromEmployee(employeeId);
    }
}
