package com.hs.hscontrolinformation.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name="documento")

public class Document implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_document")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocument;

    @NotEmpty
    @Column(name = "name_file")
    private String nameFile;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "presigned_url",length = 600)
    private String presignedUrl;

    @Column(name = "expiration_date")
    private String expirationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_Contrato", referencedColumnName = "id_Contrato")
    private Contract contract;

}
