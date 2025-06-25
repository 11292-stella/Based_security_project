package com.example.Based_security_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EsempioAssociato {

    @Id
    @GeneratedValue
    private int id;

    private String nome;
    private String descrizione;

    @ManyToOne
    private Esempio esempio;


}
