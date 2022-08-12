package com.hs.hscontrolinformation.services;

import java.util.List;

import com.hs.hscontrolinformation.dao.ClientDao;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import com.hs.hscontrolinformation.domain.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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
    @Transactional(readOnly = true)
    public List<Client> findByKeyword(String keyword){
        return repository.findAllByKeyWord("%"+keyword.toLowerCase()+"%");
    }
    @Override
    @Transactional(readOnly = false)
    public void save(Client data) {
        repository.save(data);
    }
    @Transactional(readOnly = true)
    public Page<Client> findPage(int pageNumber){
        double maxItems=15;
        Pageable pageable = PageRequest.of(pageNumber - 1,(int) maxItems);
        return repository.findAll(pageable);
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
