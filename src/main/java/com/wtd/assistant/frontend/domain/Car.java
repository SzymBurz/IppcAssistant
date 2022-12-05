package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS")
public class Car {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    int carId;
    @Column
    String name;
    @Column
    String licensePlate;
    @Column
    double mileageCounter;
    @OneToMany(
            targetEntity = Trip.class,
            mappedBy = "car",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Trip> trips;

    public Car(String name, String licensePlate, double mileageCounter) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.mileageCounter = mileageCounter;
    }

}
