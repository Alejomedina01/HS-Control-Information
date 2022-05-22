package com.hs.hscontrolinformation.service;

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
    @Transactional(readOnly = true)
    public Contract encontrar(Contract data) {
        return contractDao.findById(data.getIdContract()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataContract(){
        return contractDao.findBasicDataContract();
    }


}
