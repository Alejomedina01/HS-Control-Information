package com.hs.hscontrolinformation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "cliente")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente")
    private Long idClient;

    @NotEmpty
    @Column(name = "nombre_cliente")
    private String clientName;

    @Email
    @Column(name = "email_cliente")
    private String email;

    @Column(name = "numero_telefono_cliente")
    private String numberPhone;

    @OneToMany
    @JoinColumn(name = "id_cliente")
    private List<Contract> contractList;
}
