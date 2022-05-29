package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractDao extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente", nativeQuery = true)
    List<String> findBasicDataContract();
}
