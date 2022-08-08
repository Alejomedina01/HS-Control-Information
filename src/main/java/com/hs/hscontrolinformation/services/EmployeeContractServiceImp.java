package com.hs.hscontrolinformation.services;

import com.hs.hscontrolinformation.controllers.MyPage;
import com.hs.hscontrolinformation.dao.EmployeeContractDao;
import com.hs.hscontrolinformation.dao.EmployeeDao;
import com.hs.hscontrolinformation.domain.Employee;
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
    @Autowired
    private EmployeeDao employeeDao;
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

    private static final   double maxItems=15;

    @Transactional(readOnly = true)
    public EmployeeContract find(EmployeeContract employeeContract) {
        return employeeContractDao.findById(employeeContract.getIdEmployeeContract()).orElse(null);
    }

    @Transactional(readOnly = true)
    public MyPage<String> findPage(String idContract,int pageNumber){
        MyPage<String> myPage=new MyPage<>();
        long total = employeeDao.count() - employeeContractDao.countAllEmployeesByOneContract(idContract);
        List<String> employees=employeeContractDao.getEmployeesNotAsociated(idContract,(long)((pageNumber-1)*maxItems) , (int) maxItems);
        myPage.setTotalItems(total);
        int numberPage=(int) Math.ceil(total/maxItems);
        myPage.setNumberPages(numberPage==0?1:numberPage);
        System.out.println("numero de pagina------------"+myPage.getNumberPages());
        myPage.setContent(employees);
        return myPage;
    }
    public MyPage<String> findByKeyword(String idContract,String keyword){
        MyPage<String> myPage=new MyPage<>();
        List<String> employees=employeeContractDao.findAllByKeyWord(idContract,"%"+keyword.toLowerCase()+"%");
        myPage.setTotalItems(employees.size());
        myPage.setNumberPages(1);
        myPage.setContent(employees);
        return myPage;
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
