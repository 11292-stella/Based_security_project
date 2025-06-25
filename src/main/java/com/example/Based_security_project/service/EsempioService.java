package com.example.Based_security_project.service;

import com.example.Based_security_project.dto.EsempioDto;
import com.example.Based_security_project.exception.NotFoundException;
import com.example.Based_security_project.model.Esempio;
import com.example.Based_security_project.repository.EsempioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EsempioService {

    @Autowired
    private EsempioRepository esempioRepository;

    // CREATE
    public Esempio saveEsempio(EsempioDto esempioDto) {
        Esempio esempio = new Esempio();
        esempio.setNome(esempioDto.getNome());
        esempio.setDescrizione(esempioDto.getDescrizione());
        return esempioRepository.save(esempio);
    }

    // READ ALL (paginato)
    public Page<Esempio> getAllEsempio(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return esempioRepository.findAll(pageable);
    }

    // READ by ID
    public Esempio getEsempio(int id) throws NotFoundException {
        return esempioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Esempio con id " + id + " non trovato"));
    }

    // UPDATE
    public Esempio updateEsempio(int id, EsempioDto esempioDto) throws NotFoundException {
        Esempio esempioDaAggiornare = getEsempio(id);
        esempioDaAggiornare.setNome(esempioDto.getNome());
        esempioDaAggiornare.setDescrizione(esempioDto.getDescrizione());
        return esempioRepository.save(esempioDaAggiornare);
    }

    // DELETE
    public void deleteEsempio(int id) throws NotFoundException {
        Esempio esempioDaEliminare = getEsempio(id);
        esempioRepository.delete(esempioDaEliminare);
    }
}
