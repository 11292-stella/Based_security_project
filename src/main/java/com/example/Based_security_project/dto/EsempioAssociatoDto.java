package com.example.Based_security_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EsempioAssociatoDto {

    @NotEmpty(message = "Il nome dell'esempio associato è obbligatorio")
    private String nome;

    @NotEmpty(message = "La descrizione è obbligatoria")
    private String descrizione;

    @NotNull(message = "L'associazione a Esempio è obbligatoria")
    private Integer esempioId;

}
