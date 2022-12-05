package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ENTERPRISES")
public class Enterprise {

    //String address;
    //String postCode;
    //String telephoneNumber;
    //String email;
    //String RegistrationMark;
    //LocalDate dateOfIssue;
    //String certificateNo;
    //LocalDate firstAudit;

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    int enterpriseId;

    @Column
    String ippcCode;
    @Column
    String name;
    @Column
    LocalDate expiryDate;

    @OneToMany(
            targetEntity = Audit.class,
            mappedBy = "enterprise",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Audit> audits = new ArrayList<>();

    @OneToMany(
            targetEntity = Certificate.class,
            mappedBy = "enterprise",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Certificate> certificates = new ArrayList<>();

    public Enterprise(String ippcCode, String name) {
        this.ippcCode = ippcCode;
        this.name = name;
    }
    public Enterprise(String ippcCode, String name, LocalDate expiryDate) {
        this.ippcCode = ippcCode;
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public Enterprise() {
    }

    @Override
    public String toString() {
        return  ippcCode + " " + name;
    }
}
