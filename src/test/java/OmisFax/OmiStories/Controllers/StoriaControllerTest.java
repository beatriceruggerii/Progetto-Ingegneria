package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

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

    @InjectMocks
    private StorieController storieController;

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
        when(storiaService.responseFetchStorie()).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storieController.fetchStorie();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFiltroAutore() {
        String username = "testUser";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie filtrate per autore");
        when(storiaService.responseStorieAutore(username)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storieController.filtroAutore(username);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFiltroRicerca() {
        String titolo = "Titolo di prova";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie filtrate per titolo");
        when(storiaService.responseFiltroTitolo(titolo)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storieController.filtroRicerca(titolo, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFetchStorieUtente() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("storie", "lista di storie dell'utente");
        String username = (String)session.getAttribute("loggedUsername");
        when(storiaService.responseStorieAutore(username)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storieController.filtroAutoreInSessione(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testFetchDatiStoria() {
        String titolo = "Titolo di prova";
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("dati", "dati della storia");
        when(storiaService.responseDatiStoria(titolo)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = storiaController.fetchDatiStoria(titolo);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }
}
