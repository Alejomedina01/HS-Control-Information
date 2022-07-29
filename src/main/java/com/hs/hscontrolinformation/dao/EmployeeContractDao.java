package com.hs.hscontrolinformation.dao;

import com.hs.hscontrolinformation.domain.Employee;
import com.hs.hscontrolinformation.domain.EmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeContractDao extends JpaRepository<EmployeeContract, Long> {

    @Query(nativeQuery = true, value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado,\n"+
            " em.apodo_empleado, em.telefono \n"+
            "FROM empleado em \n"+
            "WHERE em.id_empleado \n"+
            "NOT IN (SELECT emp.id_empleado \n"+
            "FROM empleado emp \n" +
            "JOIN empleados_contratos ec \n "+
            "ON emp.id_empleado = ec.id_empleado \n"+
            "AND ec.id_contrato = ?1) LIMIT ?3 offset ?2 ")
    List<String> getEmployeesNotAsociated(String idContract, long startIndex, int maxElements);
    @Query(nativeQuery = true, value = "SELECT em.id_empleado, em.nombre_empleado, em.apellido_empleado,\n"+
            " em.apodo_empleado, em.telefono \n"+
            "FROM empleado em \n"+
            "WHERE em.id_empleado \n"+
            "NOT IN (SELECT emp.id_empleado \n"+
            "FROM empleado emp \n" +
            "JOIN empleados_contratos ec \n "+
            "ON emp.id_empleado = ec.id_empleado \n"+
            "AND ec.id_contrato = ?1) \n" +
            "AND (cast(em.id_empleado as varchar) like  ?2 \n" +
            "OR lower(em.nombre_empleado) like  ?2 \n" +
            "OR lower(em.apellido_empleado) like  ?2 \n" +
            "OR lower(em.apodo_empleado) like  ?2 ) ")
    List<String> findAllByKeyWord(String idContract,String keyWord);
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM empleados_contratos WHERE id_empleado=?1 AND id_contrato=?2")
    void deleteAsociation(Long idEmployee, String idContract);

    @Query(nativeQuery = true, value = "SELECT * FROM empleados_contratos WHERE id_empleado=?1 AND id_contrato=?2")
    EmployeeContract findByEmpCont(Long idEmployee, String idContract);

}
