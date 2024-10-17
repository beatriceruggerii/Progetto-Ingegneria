import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Controllers.StoriaController;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Services.StoriaService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class StoriaControllerTest {

    @InjectMocks
    private StoriaController storiaController;

    @Mock
    private StoriaService storiaService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaStoria() {
        StoriaDTO mockPayload = new StoriaDTO("Titolo", "Descrizione");
        when(storiaService.salvaStoria(mockPayload, session)).thenReturn(new ResponseEntity<>("Storia salvata con successo", HttpStatus.OK));

        ResponseEntity<String> response = storiaController.salvaStoria(mockPayload, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Storia salvata con successo");
    }

    @Test
    void testFetchStorie() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie");
        when(storiaService.responseFetchStorie(session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = storiaController.fetchStorie(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFiltroAutore() {
        String username = "testUser";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie filtrate per autore");
        when(storiaService.responseFiltroAutore(username, session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = storiaController.filtroAutore(username, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFiltroRicerca() {
        String titolo = "Titolo di prova";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie filtrate per titolo");
        when(storiaService.responseFiltroTitolo(titolo, session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = storiaController.filtroRicerca(titolo, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFetchStorieUtente() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie dell'utente");
        when(storiaService.responseStorieAutore(session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = storiaController.fetchStorieUtente(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFetchDatiStoria() {
        String titolo = "Titolo di prova";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("dati", "dati della storia");
        when(storiaService.responseDatiStoria(titolo, session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = storiaController.fetchDatiStoria(titolo, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }
}
