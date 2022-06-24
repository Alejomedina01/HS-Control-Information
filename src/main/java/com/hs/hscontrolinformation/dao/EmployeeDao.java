package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cn.estado_contrato, cl.nombre_cliente\n" +
            "FROM ((contrato cn JOIN cliente cl \n" +
            "ON cn.id_cliente = cl.id_cliente) JOIN empleados_contratos ec\n" +
            "ON cn.id_contrato = ec.id_contrato\n" +
            "AND ec.id_empleado = ?1)", nativeQuery = true)
    List<String> findContractsFromEmployee(Long idEmployee);
}
