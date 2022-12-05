package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Audit {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    public int auditId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User user;

    @ManyToOne
    @JoinColumn(name = "ENTERPRISE_ID")
    public Enterprise enterprise;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    public Trip tripId;

    @Column(name = "DATE")
    public LocalDate date;

    @Column
    Boolean completed;

    @Column
    String remarks;

    @Column
    LocalDate secondTerm;

    public Audit(Enterprise enterprise, User user, LocalDate date) {
        this.enterprise = enterprise;
        this.user = user;
        this.date = date;
    }
}