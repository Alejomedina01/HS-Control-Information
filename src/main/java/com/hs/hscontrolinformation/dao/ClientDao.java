package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM cliente ", nativeQuery = true)
    List<String> findBasicDataClient();
    Client findByIdClient(Long idClient);
}
