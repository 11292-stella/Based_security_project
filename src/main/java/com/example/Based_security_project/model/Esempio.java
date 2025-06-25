package com.example.Based_security_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Esempio {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String descrizione;

    @OneToMany(mappedBy = "esempio")
    @JsonIgnore
    private List<EsempioAssociato> esempioAssociati;
}
