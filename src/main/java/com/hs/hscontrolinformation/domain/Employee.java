package com.hs.hscontrolinformation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotNull
    @Column(name = "telefono")
    private Long numberPhone;

    @NotEmpty
    @Column(name = "eps")
    private String eps;

    @NotEmpty
    @Column(name = "afp")
    private String afp;

    @NotEmpty
    @Column(name = "arl")
    private String arl;
}
