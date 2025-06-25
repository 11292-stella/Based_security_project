// Import di base per i test con JUnit e Mockito
import com.example.Based_security_project.dto.EsempioAssociatoDto;
import com.example.Based_security_project.dto.EsempioDto;
import com.example.Based_security_project.model.Esempio;
import com.example.Based_security_project.model.EsempioAssociato;
import com.example.Based_security_project.repository.EsempioAssociatoRepository;
import com.example.Based_security_project.repository.EsempioRepository;
import com.example.Based_security_project.service.EsempioAssociatoService;
import com.example.Based_security_project.service.EsempioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Attiva Mockito per simulare repository e dipendenze
@ExtendWith(MockitoExtension.class)
public class BasedTest {

    // Mock della repository principale
    @Mock
    private EsempioRepository esempioRepository;

    // Inietta i mock nel service che stiamo testando
    @InjectMocks
    private EsempioService esempioService;

    // Mock di una repository o entità associata (opzionale, rimuovi se non serve)
    @Mock
    private EsempioAssociatoRepository esempioAssociatoRepository;

    @InjectMocks
    EsempioAssociatoService esempioAssociatoService;







    // TEST: salvataggio di un'entità partendo da un DTO
    @Test
    public void testSaveEsempioConDto() throws Exception {
        // Prepara il DTO
        EsempioDto esempioDto = new EsempioDto();
        esempioDto.setNome("Questo nome");
        esempioDto.setDescrizione("Questa descrizione");


        // Prepara l'entità associata (es. relazione @ManyToOne)
        EsempioAssociato esempioAssociato = new EsempioAssociato();
        esempioAssociato.setId(1);
        esempioAssociato.setNome("Relazione Nome");

        // Prepara l'entità da salvare
        Esempio salvato = new Esempio();
        salvato.setId(1);
        salvato.setNome(esempioDto.getNome());
        salvato.setDescrizione(esempioDto.getDescrizione());


        // Mock dei metodi repository

        when(esempioRepository.save(any(Esempio.class))).thenReturn(salvato);

        // Esecuzione
        Esempio result = esempioService.saveEsempio(esempioDto);

        // Verifiche
        assertNotNull(result);
        assertEquals("Questo nome", result.getNome());
        assertEquals("Questa descrizione", result.getDescrizione());

    }

    // TEST: recupero di un'entità per ID (esistente)
    @Test
    public void testGetEsempio() throws Exception {
        Esempio esempio = new Esempio();
        esempio.setId(1);
        esempio.setNome("Un Nome");
        esempio.setDescrizione("Descrizione");

        when(esempioRepository.findById(1)).thenReturn(Optional.of(esempio));

        Esempio result = esempioService.getEsempio(1);

        assertNotNull(result);
        assertEquals("Un Nome", result.getNome());
    }

    // TEST: recupero fallito per entità inesistente
    @Test
    public void testGetEsempioNotFound() {
        when(esempioRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            esempioService.getEsempio(2);
        });

        assertTrue(exception.getMessage().contains("non trovato"));
    }

    // TEST: aggiornamento entità tramite DTO
    @Test
    public void testSaveEsempioAssociatoConDto() throws Exception {
        EsempioAssociatoDto dto = new EsempioAssociatoDto();
        dto.setNome("Relazione Nome");
        dto.setDescrizione("Descrizione");
        dto.setEsempioId(1); // ✔️ Questo campo esiste

        Esempio esempio = new Esempio();
        esempio.setId(1);
        esempio.setNome("Esempio legato");

        EsempioAssociato entity = new EsempioAssociato();
        entity.setId(1);
        entity.setNome(dto.getNome());
        entity.setDescrizione(dto.getDescrizione());
        entity.setEsempio(esempio);

        when(esempioRepository.findById(1)).thenReturn(Optional.of(esempio));
        when(esempioAssociatoRepository.save(any(EsempioAssociato.class))).thenReturn(entity);

        EsempioAssociato result = esempioAssociatoService.saveEsempioAssociato(dto);

        assertNotNull(result);
        assertEquals("Relazione Nome", result.getNome());
        assertEquals("Esempio legato", result.getEsempio().getNome());
    }

    // TEST: eliminazione di un'entità
    @Test
    public void testDeleteEsempio() throws Exception {
        Esempio esempio = new Esempio();
        esempio.setId(1);
        esempio.setNome("Nome");

        when(esempioRepository.findById(1)).thenReturn(Optional.of(esempio));

        assertDoesNotThrow(() -> esempioService.deleteEsempio(1));
        verify(esempioRepository, times(1)).delete(esempio);
    }
}