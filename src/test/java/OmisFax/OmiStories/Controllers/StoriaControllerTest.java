package OmisFax.OmiStories.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.StoriaService;
import OmisFax.OmiStories.Services.StoryDataService;
import OmisFax.OmiStories.Services.interfaces.IStoriaService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class StoriaControllerTest {

    @InjectMocks
    private StoriaController storiaController;

    @Mock
    private StoriaService storiaService;

    @Mock
    private StoryDataService storyDataService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaStoria() {
        // Configura il payload e i mock necessari
        StoriaDTO mockPayload = new StoriaDTO("Titolo", "Descrizione");
        String username = "testUser";

        // Simula il valore di "loggedUsername" nella sessione
        when(session.getAttribute("loggedUsername")).thenReturn(username);

        // Simula la risposta del servizio
        when(storiaService.salvaStoria(mockPayload, username)).thenReturn(new Storia());

        // Esegui il metodo
        ResponseEntity<String> response = storiaController.salvaStoria(mockPayload, session);

        // Verifica il risultato, aggiornando il messaggio atteso per includere il punto alla fine
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Storia salvata con successo.", response.getBody());
    }

    @Test
    void testFetchDatiStoria() {
        // Configura il titolo e il mock della risposta
        String titolo = "Titolo di prova";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("dati", "dati della storia");

        // Simula la risposta del servizio
        when(storyDataService.getCompleteStoriaData(titolo)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storiaController.fetchDatiStoria(titolo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}

