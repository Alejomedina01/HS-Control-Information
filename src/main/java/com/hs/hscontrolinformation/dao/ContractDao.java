package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractDao extends JpaRepository<Contract, Long> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente FROM contrato AS cn JOIN cliente AS cl ON cn.id_cliente = cl.id_cliente", nativeQuery = true)
    List<String> findBasicDataContract();

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE contrato SET id_cliente = ?1 WHERE id_contrato = ?2")
    void updateContractToClientId(Long idClient, Long idContract);

    @Query(nativeQuery = true, value = "SELECT id_cliente FROM contrato WHERE id_contrato = ?1")
    String findClientIdFromContract(Long idContract);

//    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, em.telefono FROM empleado em WHERE em.id_empleado IN (SELECT emp.id_empleado FROM empleado emp JOIN empleados_contratos ec ON emp.id_empleado = ec.id_empleado AND ec.id_contrato = ?1)", nativeQuery = true)
    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, emc.fecha_inicio, emc.fecha_final FROM empleado em JOIN empleados_contratos emc ON em.id_empleado = emc.id_empleado AND emc.id_contrato = ?1", nativeQuery = true)
    List<String> getEmployeesAsociated(Long idContract);
}