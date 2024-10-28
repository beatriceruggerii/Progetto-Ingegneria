package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.SceltaDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.SceltaService;
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

public class SceltaControllerTest {

    @InjectMocks
    private SceltaController sceltaController;

    @InjectMocks
    private ScelteController scelteController;

    @Mock
    private SceltaService sceltaService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaScelta() {
        SceltaDTO mockSceltaDTO = new SceltaDTO();
        when(sceltaService.responseSalvaScelta(mockSceltaDTO, session)).thenReturn(new ResponseEntity<>("Scelta salvata con successo", HttpStatus.OK));

        ResponseEntity<String> response = sceltaController.salvaScelta(mockSceltaDTO, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Scelta salvata con successo");
    }

    @Test
    void testFetchScelte() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("scelte", "lista di scelte");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        when(sceltaService.responseFetchScelte(storia)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = scelteController.fetchScelte(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testModificaSceltaSuccess() {
        Long idScelta = 1L;
        SceltaDTO nuovaScelta = new SceltaDTO();
        when(sceltaService.modificaScelta(nuovaScelta)).thenReturn(true);

        ResponseEntity<String> response = sceltaController.modificaIndovinello(idScelta, nuovaScelta);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Modifica avvenuta con successo.");
    }

    @Test
    void testModificaSceltaFailure() {
        Long idScelta = 1L;
        SceltaDTO nuovaScelta = new SceltaDTO();
        when(sceltaService.modificaScelta(nuovaScelta)).thenReturn(false);

        ResponseEntity<String> response = sceltaController.modificaIndovinello(idScelta, nuovaScelta);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody().equals("Modifica fallita: Scelta non trovata.");
    }
}