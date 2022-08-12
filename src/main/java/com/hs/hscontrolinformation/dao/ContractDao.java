package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractDao extends JpaRepository<Contract, String> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente \n" +
            "FROM contrato AS cn \n" +
            "JOIN cliente AS cl \n" +
            "ON cn.id_cliente = cl.id_cliente \n" +
            "LIMIT ?2 offset ?1 \n", nativeQuery = true)
    List<String> findBasicDataContract(long startIndex,int maxContracts);

    @Query(nativeQuery = true, value = "SELECT id_cliente FROM contrato WHERE id_contrato = ?1")
    String findClientIdFromContract(String idContract);

    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, emc.fecha_inicio, emc.fecha_final \n" +
            "FROM empleado em \n" +
            "JOIN empleados_contratos emc \n" +
            "ON em.id_empleado = emc.id_empleado \n" +
            "AND emc.id_contrato = ?1 \n" +
            "LIMIT ?3 offset ?2", nativeQuery = true)
    List<String> getEmployeesAsociated(String idContract,long startIndex,int maxContracts);
    @Query(value = "SELECT count(em.id_empleado) \n" +
            "FROM empleado em \n" +
            "JOIN empleados_contratos emc \n" +
            "ON em.id_empleado = emc.id_empleado \n" +
            "AND emc.id_contrato = ?1 ", nativeQuery = true)
    long countEmployeesAsociated(String idContract);
    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente \n" +
            "FROM contrato AS cn \n" +
            "JOIN cliente AS cl \n" +
            "ON cn.id_cliente = cl.id_cliente \n" +
            "WHERE LOWER(cn.id_contrato) LIKE ?1 \n" +
            "OR LOWER(cn.nombre_proyecto) LIKE ?1 \n" +
            "OR LOWER(cn.estado_contrato) LIKE ?1 \n" +
            "OR LOWER(cl.nombre_cliente) LIKE ?1", nativeQuery = true)
    List<String> findAllByKeyWord(String keyWord);
    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, \n" +
            " emc.fecha_inicio, emc.fecha_final \n" +
            "FROM empleado em \n" +
            "JOIN empleados_contratos emc \n" +
            "ON em.id_empleado = emc.id_empleado \n" +
            "AND emc.id_contrato = ?1 \n" +
            "WHERE cast(em.id_empleado as varchar) LIKE ?2 \n" +
            "OR LOWER(em.nombre_empleado) LIKE ?2 \n" +
            "OR LOWER(em.apellido_empleado) LIKE ?2 \n" +
            "OR LOWER(em.apodo_empleado) LIKE ?2 ", nativeQuery = true)
    List<String> getEmployeesByKeyWord(String idContract, String keyWord);
}