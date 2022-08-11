package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, String> {

    @Query(value = "SELECT * FROM cliente ", nativeQuery = true)
    List<String> findBasicDataClient();
    Client findByIdClient(Long idClient);

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente AND cn.id_cliente = ?1", nativeQuery = true)
    List<String> findContractsFromClient(String idClient);

    @Query(value = "SELECT cl.id_cliente, cl.nombre_cliente, cl.email_cliente, cl.numero_telefono_cliente FROM cliente AS cl WHERE LOWER(cl.id_cliente)  LIKE ?1 OR LOWER(cl.nombre_cliente) LIKE ?1 OR  LOWER(cl.email_cliente) LIKE ?1 OR LOWER(cast(cl.numero_telefono_cliente as varchar)) LIKE ?1 ", nativeQuery = true)
    List<Client>findAllByKeyWord(String keyword);
    @Modifying
    @Query(value = "UPDATE cliente SET email_cliente = ?1 , nombre_cliente = ?2, numero_telefono_cliente = ?3 WHERE id_cliente LIKE ?4", nativeQuery = true)
    public void updateClient(String email, String clientName, long numberPhone, String idCient);
}
