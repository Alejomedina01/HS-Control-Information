package com.hs.hscontrolinformation.services;

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

    @Override
    public void delete(Contract data) {
        contractDao.delete(data);
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

    @Transactional(readOnly = true)
    public List<String> findBasicDataContract(){
        return contractDao.findBasicDataContract();
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
    public List<String> getEmployeesAsociated(String idContract){
        return contractDao.getEmployeesAsociated(idContract);
    }
}
