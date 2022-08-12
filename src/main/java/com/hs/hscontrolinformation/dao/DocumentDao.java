package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Document;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  DocumentDao extends JpaRepository<Document,Long>{

    @Query(nativeQuery = true,value = "SELECT * FROM documento WHERE id_contrato = ?1")
    List<Document>findAllDocumentsOneContract(String idContract);

    @Query(nativeQuery = true,value = "SELECT count(*) FROM documento")
    int getTotalCountDocuments();

    @Query(nativeQuery = true,value = "SELECT dc.id_contrato FROM documento AS dc WHERE id_document = ?1")
    long findIdContractForDocument(Long idDocument);
    @Query(nativeQuery = true, value = "SELECT * FROM documento WHERE id_contrato = ?1 AND name_file LIKE ?2")
    Document findDocumentUniqName(String idContract, String nameFile);
}
