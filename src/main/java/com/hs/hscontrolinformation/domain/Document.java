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
}
