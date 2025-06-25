package com.example.Based_security_project.service;

import com.example.Based_security_project.dto.EsempioAssociatoDto;
import com.example.Based_security_project.exception.NotFoundException;
import com.example.Based_security_project.model.Esempio;
import com.example.Based_security_project.model.EsempioAssociato;
import com.example.Based_security_project.repository.EsempioAssociatoRepository;
import com.example.Based_security_project.repository.EsempioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EsempioAssociatoService {
    @Autowired
    private EsempioAssociatoRepository esempioAssociatoRepository;

    @Autowired
    private EsempioRepository esempioRepository;

    // CREATE: salva un nuovo EsempioAssociato con Esempio collegato
    public EsempioAssociato saveEsempioAssociato(EsempioAssociatoDto dto) throws NotFoundException {
        // Trova l'entità Esempio da collegare
        Esempio esempio = esempioRepository.findById(dto.getEsempioId())
                .orElseThrow(() -> new NotFoundException("Esempio non trovato"));

        // Crea nuova entità e assegna i valori
        EsempioAssociato entity = new EsempioAssociato();
        entity.setNome(dto.getNome());
        entity.setDescrizione(dto.getDescrizione());
        entity.setEsempio(esempio); // relazione

        return esempioAssociatoRepository.save(entity);
    }

    // READ ALL: restituisce tutti gli EsempioAssociato in modo paginato
    public Page<EsempioAssociato> getAllEsempioAssociato(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return esempioAssociatoRepository.findAll(pageable);
    }

    // READ ONE: restituisce un EsempioAssociato per ID
    public EsempioAssociato getEsempioAssociato(int id) throws NotFoundException {
        return esempioAssociatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EsempioAssociato con id " + id + " non trovato"));
    }

    // UPDATE: aggiorna un EsempioAssociato esistente
    public EsempioAssociato updateEsempioAssociato(int id, EsempioAssociatoDto dto) throws NotFoundException {
        EsempioAssociato entity = getEsempioAssociato(id);

        entity.setNome(dto.getNome());
        entity.setDescrizione(dto.getDescrizione());

        // Se è stato cambiato l'esempio collegato
        if (entity.getEsempio().getId() != dto.getEsempioId()) {
            Esempio nuovoEsempio = esempioRepository.findById(dto.getEsempioId())
                    .orElseThrow(() -> new NotFoundException("Esempio non trovato"));
            entity.setEsempio(nuovoEsempio);
        }

        return esempioAssociatoRepository.save(entity);
    }

    // DELETE: elimina un EsempioAssociato per ID
    public void deleteEsempioAssociato(int id) throws NotFoundException {
        EsempioAssociato entity = getEsempioAssociato(id);
        esempioAssociatoRepository.delete(entity);
    }

}
