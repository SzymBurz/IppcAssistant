package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Certificate")
public class Certificate {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    int certificate_id;

    @Column
    String name;

    @ManyToOne
    @JoinColumn(name = "ENTERPRISE_ID")
    Enterprise enterprise;

    @Column
    LocalDate dateOfIssue;

    @Column
    LocalDate expiryDate;

}
