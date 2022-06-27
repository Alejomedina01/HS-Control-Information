package com.hs.hscontrolinformation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "empleado")
public class Employee {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_empleado", nullable = false)
    private Long idEmployee;

    @NotEmpty
    @Column(name = "nombre_empleado")
    private String name;

    @NotEmpty
    @Column(name = "apellido_empleado")
    private String lastname;

    @Column(name = "apodo_empleado")
    private String nickname;

    @NotEmpty
    @Column(name = "direccion_empleado")
    private String adress;

    @Column(name = "telefono")
    private String numberPhone;

//    @NotEmpty
//    @Column(name = "lugar_trabajo")
//    private String workPlace;

    @Column(name = "eps")
    private String eps;

    @Column(name = "afp")
    private String afp;

    @Column(name = "arl")
    private String arl;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
    private List<EmployeeContract> employeeContracts;

    public List<Contract> getContracts(){
        return getEmployeeContracts()
                .stream()
                .map(employeeContract -> employeeContract.getContract())
                .collect(Collectors.toList());
    }*/
}
