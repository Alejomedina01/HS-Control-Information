package com.hs.hscontrolinformation.services;

import java.util.List;

import com.hs.hscontrolinformation.dao.ClientDao;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import com.hs.hscontrolinformation.domain.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientImplService implements ServiceTemplate<Client>{

    @Autowired
    private ClientDao repository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> list() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Client data) {
        repository.save(data);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Client data) {
        repository.delete(data);
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataClient(){
        return repository.findBasicDataClient();
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataContract(String clientId){
        return repository.findContractsFromClient(clientId);
    }

}
