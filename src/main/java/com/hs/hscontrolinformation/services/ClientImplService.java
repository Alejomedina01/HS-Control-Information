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
    ClientDao repository; 

    @Override
    @Transactional(readOnly = true)
    public List<Client> listar() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void guardar(Client data) {
        repository.save(data);
    }

    @Override
    @Transactional(readOnly = false)
    public void eliminar(Client data) {
        repository.delete(data);
    }
    @Transactional(readOnly = false)
    public void deleteforId(long id) {
        repository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Client encontrar(Long id) {
        return repository.findByIdClient(id);
    }

    @Transactional(readOnly = true)
    public List<String> findBasicDataClient(){
        return repository.findBasicDataClient();
    }
    
}
