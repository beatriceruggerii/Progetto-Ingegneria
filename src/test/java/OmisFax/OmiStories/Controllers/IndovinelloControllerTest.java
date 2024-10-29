package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.IndovinelloService;
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

public class IndovinelloControllerTest {

    @InjectMocks
    private IndovinelloController indovinelloController;

    @InjectMocks
    private IndovinelliController indovinelliController;

    @Mock
    private IndovinelloService indovinelloService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaIndovinello() {
        IndovinelloDTO mockIndovinelloDTO = new IndovinelloDTO();
        when(indovinelloService.responseSalvaIndovinello(mockIndovinelloDTO)).thenReturn("Indovinello salvato con successo");

        ResponseEntity<String> response = indovinelloController.salvaIndovinello(mockIndovinelloDTO);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Indovinello salvato con successo");
    }

    @Test
    void testFetchIndovinelli() {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("indovinelli", "lista di indovinelli");
        when(indovinelloService.responseFetchIndovinelli(storia)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = indovinelliController.fetchIndovinelli(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testModificaIndovinelloSuccess() {
        Long idIndovinello = 1L;
        IndovinelloDTO nuovoIndovinello = new IndovinelloDTO();
        when(indovinelloService.modificaIndovinello(idIndovinello, nuovoIndovinello)).thenReturn(true);

        ResponseEntity<String> response = indovinelloController.modificaIndovinello(idIndovinello, nuovoIndovinello);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Modifica avvenuta con successo.");
    }

    @Test
    void testModificaIndovinelloFailure() {
        Long idIndovinello = 1L;
        IndovinelloDTO nuovoIndovinello = new IndovinelloDTO();
        when(indovinelloService.modificaIndovinello(idIndovinello, nuovoIndovinello)).thenReturn(false);

        ResponseEntity<String> response = indovinelloController.modificaIndovinello(idIndovinello, nuovoIndovinello);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody().equals("Modifica fallita: Indovinello non trovato.");
    }
}
