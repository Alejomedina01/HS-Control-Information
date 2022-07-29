package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractDao extends JpaRepository<Contract, String> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente LIMIT ?2 offset ?1 ", nativeQuery = true)
    List<String> findBasicDataContract(long startIndex,int maxContracts);
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE contrato SET id_cliente = ?1 WHERE id_contrato = ?2")
    void updateContractToClientId(String idClient, String idContract);

    @Query(nativeQuery = true, value = "SELECT id_cliente FROM contrato WHERE id_contrato = ?1")
    String findClientIdFromContract(String idContract);

    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, emc.fecha_inicio, emc.fecha_final FROM empleado em JOIN empleados_contratos emc ON em.id_empleado = emc.id_empleado AND emc.id_contrato = ?1", nativeQuery = true)
    List<String> getEmployeesAsociated(String idContract);
    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente WHERE LOWER(cn.id_cliente) LIKE ?1 OR LOWER(cn.nombre_proyecto) LIKE ?1 OR LOWER(cn.estado_contrato) LIKE ?1 OR LOWER(cl.nombre_cliente) LIKE ?1", nativeQuery = true)
    List<String> findAllByKeyWord(String keyWord);
}