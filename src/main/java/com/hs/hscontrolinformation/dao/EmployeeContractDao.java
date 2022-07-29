package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.EmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeContractDao extends JpaRepository<EmployeeContract, Long> {

    @Query(nativeQuery = true, value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado, em.apodo_empleado, em.telefono FROM empleado em WHERE em.id_empleado NOT IN (SELECT emp.id_empleado FROM empleado emp JOIN empleados_contratos ec ON emp.id_empleado = ec.id_empleado AND ec.id_contrato = ?1)")
    List<String> getEmployeesNotAsociated(String idContract);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM empleados_contratos WHERE id_empleado=?1 AND id_contrato=?2")
    void deleteAsociation(Long idEmployee, String idContract);

    @Query(nativeQuery = true, value = "SELECT * FROM empleados_contratos WHERE id_empleado=?1 AND id_contrato=?2")
    EmployeeContract findByEmpCont(Long idEmployee, String idContract);

}
