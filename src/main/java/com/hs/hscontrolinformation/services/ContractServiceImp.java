package com.hs.hscontrolinformation.services;

import com.hs.hscontrolinformation.dao.ContractDao;
import com.hs.hscontrolinformation.domain.Contract;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractServiceImp  implements ServiceTemplate<Contract> {

    @Autowired
    private ContractDao contractDao;

    @Override
    @Transactional(readOnly = true)
    public List<Contract> listar() {
        return (List<Contract>) contractDao.findAll();
    }

    @Override
    public void guardar(Contract data) {
        contractDao.save(data);
    }

    @Override
    public void eliminar(Contract data) {
        contractDao.delete(data);
    }

    @Override
    public Contract encontrar(Long id) {
        return null;
    }

    @Transactional(readOnly = true)
    public Contract encontrar(Contract contract) {
        return contractDao.findById(contract.getIdContract()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataContract(){
        return contractDao.findBasicDataContract();
    }

    @Transactional
    public void updateContractToClientId(Long idClient, Long idContract){
        contractDao.updateContractToClientId(idClient, idContract);
    }

    @Transactional(readOnly = true)
    public String findClientIdFromContract(Long idContract){
        return contractDao.findClientIdFromContract(idContract);
    }
}
