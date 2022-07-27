package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT cn.id_contrato, cn.nombre_proyecto, cl.nombre_cliente, ec.fecha_inicio, ec.fecha_final\n" +
            "FROM ((contrato cn JOIN cliente cl \n" +
            "ON cn.id_cliente = cl.id_cliente) JOIN empleados_contratos ec\n" +
            "ON cn.id_contrato = ec.id_contrato\n" +
            "AND ec.id_empleado = ?1)", nativeQuery = true)
    List<String> findContractsFromEmployee(Long idEmployee);
    @Query(value = "SELECT em.id_empleado, em.nombre_empleado, em.apodo_empleado, em.telefono\n" +
            "FROM empleado em WHERE cast( em.id_empleado as varchar) LIKE ?1 OR em.nombre_empleado LIKE ?1 OR em.apodo_empleado LIKE ?1 OR em.telefono LIKE ?1", nativeQuery = true)
    List<Employee> findAllByKeyWord(String keyWord);
}
