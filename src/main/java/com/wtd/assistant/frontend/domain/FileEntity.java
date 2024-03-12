package com.wtd.assistant.frontend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "FILES")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column
    private String fileName;

    @Column
    private String contentType;

    @Lob
    private byte[] data;


}
