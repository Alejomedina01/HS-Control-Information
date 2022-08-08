package com.hs.hscontrolinformation.services;

import com.hs.hscontrolinformation.controllers.MyPage;
import com.hs.hscontrolinformation.dao.ContractDao;
import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractServiceImp implements ServiceTemplate<Contract> {

    @Autowired
    private ContractDao contractDao;

    @Override
    @Transactional(readOnly = true)
    public List<Contract> list() {
        return (List<Contract>) contractDao.findAll();
    }

    @Override
    public void save(Contract data) {
        contractDao.save(data);
    }

    @Transactional(readOnly = true)
    public MyPage<String> findPage(int pageNumber){
        double maxItems=15;
        MyPage<String> myPage=new MyPage<>();
        long total= contractDao.count();
        List<String> contracts=contractDao.findBasicDataContract((long)((pageNumber-1)*maxItems) , (int) maxItems);
        myPage.setTotalItems(total);
        myPage.setNumberPages((int) Math.ceil(total/maxItems));
        myPage.setContent(contracts);
        return myPage;
    }
    @Override
    public void delete(Contract data) {
        contractDao.delete(data);
    }
    @Transactional(readOnly = true)
    public List<String> findByKeyword(String keyword){
        return contractDao.findAllByKeyWord("%"+keyword.toLowerCase()+"%");
    }
    @Override
    public Contract findById(Long id) {
        return null;
    }

    @Override
    public Contract findById(String id) {
        return contractDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Contract find(Contract contract) {
        return contractDao.findById(contract.getIdContract()).orElse(null);
    }

    @Transactional
    public void updateContractToClientId(String idClient, String idContract){
        contractDao.updateContractToClientId(idClient, idContract);
    }

    @Transactional(readOnly = true)
    public String findClientIdFromContract(String idContract){
        return contractDao.findClientIdFromContract(idContract);
    }

    @Transactional(readOnly = true)
    public MyPage<String> getEmployeesAsociated(String idContract,int page){
        double maxItems=15;
        MyPage<String> myPage=new MyPage();
        long totalEmployees= contractDao.countEmployeesAsociated(idContract);
        List<String> employees = contractDao.getEmployeesAsociated(idContract,(long)((page-1)*maxItems) ,(int)maxItems);
        int numberPage=(int) Math.ceil(totalEmployees/maxItems);
        myPage.setNumberPages(numberPage==0?1:numberPage);
        myPage.setTotalItems(totalEmployees);
        myPage.setContent(employees);
        return myPage;
    }
    @Transactional(readOnly = true)
    public MyPage<String> getEmployeesByKeyWork(String idContract,String keyWord) {
        MyPage<String> myPage = new MyPage();
        List<String> employees=contractDao.getEmployeesByKeyWord(idContract,"%"+keyWord.toLowerCase()+"%");
        myPage.setNumberPages(1);
        myPage.setTotalItems(employees.size());
        myPage.setContent(employees);
        return myPage;
    }
}
