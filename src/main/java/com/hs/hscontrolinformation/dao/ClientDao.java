package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM cliente ", nativeQuery = true)
    List<String> findBasicDataClient();
    Client findByIdClient(Long idClient);

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente AND cn.id_cliente = ?1", nativeQuery = true)
    List<String> findContractsFromClient(Long idClient);
}
