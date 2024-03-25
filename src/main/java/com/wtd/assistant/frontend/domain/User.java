package com.wtd.assistant.frontend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(unique = true)
    private int userId;

    @Column
    private String name;

    @Column
    private String password;

    @OneToMany(
            targetEntity = Audit.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<Audit> audits = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
}
