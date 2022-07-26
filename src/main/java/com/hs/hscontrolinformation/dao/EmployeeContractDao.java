package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeContractDao extends JpaRepository<EmployeeContract, Long> {

    @Query(nativeQuery = true, value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, em.telefono FROM empleado em WHERE em.id_empleado NOT IN (SELECT emp.id_empleado FROM empleado emp JOIN empleados_contratos ec ON emp.id_empleado = ec.id_empleado AND ec.id_contrato = ?1)")
    List<String> getEmployeesNotAsociated(String idContract);
}
