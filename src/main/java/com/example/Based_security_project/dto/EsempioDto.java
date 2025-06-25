package com.example.Based_security_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsempioDto {
    @NotEmpty(message = "Il nome del muscolo è obbligatorio")
    private String nome;

    @NotEmpty(message = "La descrizione è obbligatoria")
    private String descrizione;

    @NotNull(message = "Devi associare un muscolo all'esercizio")
    private Integer esempioId;
}
