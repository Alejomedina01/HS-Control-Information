package com.hs.hscontrolinformation.domain;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "contrato")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_contrato")
    private Long idContract;

    @NotEmpty
    @Column(name = "nombre_proyecto")
    private String projectName;

    @NotEmpty
    @Column(name = "ciudad_contrato")
    private String city;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    @Column(name = "fecha_contrato")
    private Date contractDate;

    @NotNull
    @Column(name = "valor_inicial")
    private Long initialValue;

    @Column(name = "valor_adicional")
    private Long aditionalValue;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_acta_inicio")
    private Date initialDateAct;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_acta_recibo")
    private Date receivalDateAct;

    @NotNull
    @Column(name = "valor_facturado")
    private Long invoicedValue;

    @Column(name = "valor_pendiente")
    private Long pendingValue;

    @NotNull
    @Column(name = "valor_retegarantia")
    private Long warratyValue;

    @NotEmpty
    @Column(name = "estado_garantia")
    private String warrantyState;

    @NotEmpty
    @Column(name = "estado_contrato")
    private String contractState;

    @OneToMany
    @JoinColumn(name = "id_contrato")
    private List<Document> documentsList;


/*    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
    private List<EmployeeContract> employeeContracts;

    public List<Employee> getEmployees(){
        return getEmployeeContracts()
                .stream()
                .map(employeeContract -> employeeContract.getEmployee())
                .collect(Collectors.toList());
    }*/
}
