package com.wtd.assistant.frontend.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRIPS")
public class Trip {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    int tripId;

    @ManyToOne
    @JoinColumn(name = "CAR_ID")
    Car car;

    @Column
    double carCounterBefore;

    @Column
    double carCounterAfter;


    @OneToMany(
            targetEntity = Audit.class,
            mappedBy = "tripId",
            //cascade = CascadeType.ALL,
            //cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    private List<Audit> audits = new ArrayList<>();

    @OneToMany(
            targetEntity = Expense.class,
            mappedBy = "tripId",
            //cascade = CascadeType.ALL,
            //cascade = CascadeType.REFRESH,
            //cascade = CascadeType.MERGE,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    private List<Expense> expenses = new ArrayList<>();

    @Column
    double workTime;

    @Column
    LocalDate firstDay;

    @Column
    LocalDate secondDay;

    public Trip(Car car,
                double carCounterBefore, double carCounterAfter,
                List<Audit> audits, List<Expense> expenses,
                double workTime, LocalDate firstDay, LocalDate secondDay) {
        this.car = car;
        this.carCounterBefore = carCounterBefore;
        this.carCounterAfter = carCounterAfter;
        this.audits = audits;
        this.expenses = expenses;
        this.workTime = workTime;
        this.firstDay = firstDay;
        this.secondDay = secondDay;
    }

    public Trip(List<Audit> audits, List<Expense> expenses) {
        this.audits = audits;
        this.expenses = expenses;
    }

    public String daysToString() {
        if (firstDay != null && secondDay != null) {
            return  firstDay +
                    ", " + secondDay;
        } else if (firstDay != null && secondDay == null) {
            return  firstDay.toString();
        } else {
            return " ";
        }
    }

}
