package com.hs.hscontrolinformation.services;

import java.util.List;

import com.hs.hscontrolinformation.dao.DocumentDao;
import com.hs.hscontrolinformation.util.ServiceTemplate;
import com.hs.hscontrolinformation.domain.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class DocumentServiceImp implements ServiceTemplate<Document>{
    @Autowired
    private DocumentDao repository;

    @Transactional
    public List<Document> findAllDocumentsOneContract(String idContract){
        return repository.findAllDocumentsOneContract(idContract);
    }
    @Transactional
    public long findIdContractForDocument(long idDocument){
        return repository.findIdContractForDocument(idDocument);
    }
    @Transactional
    public int getTotalCountDocuments(){
        return  repository.getTotalCountDocuments();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Document> list() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(Document data) {
        repository.save(data);
    }

    @Override
    public void delete(Document data) {
        repository.delete(data);
    }

    @Override
    @Transactional(readOnly = true)
    public Document findById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Document findById(String id) {
        return null;
    }

    @Transactional(readOnly = true)
    public Document findDocumentUniqName(String idContract, String nameFile){
        return repository.findDocumentUniqName(idContract, nameFile);
    }
}
