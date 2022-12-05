package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EXPENSES")
public class Expense {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    int expense_id;

    @ManyToOne
    @JoinColumn(name = "TRIP_ID")
    public Trip tripId;

    @Column
    double amount;

    @Column
    String description;

    @Override
    public String toString() {
        return tripId + " " + amount + " " + description;
    }

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }
}
