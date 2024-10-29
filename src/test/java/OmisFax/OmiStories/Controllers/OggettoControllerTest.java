package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.OggettoDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.OggettoService;
import OmisFax.OmiStories.Services.ScenarioService;
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

public class OggettoControllerTest {

    @InjectMocks
    private OggettoController oggettoController;

    @InjectMocks
    private OggettiController oggettiController;

    @Mock
    private OggettoService oggettoService;

    @Mock
    private ScenarioService scenarioService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaOggetto() {
        OggettoDTO mockOggettoDTO = new OggettoDTO();
        Storia mockStoria = new Storia();
        when(session.getAttribute("storiaCorrente")).thenReturn(mockStoria);
        when(oggettoService.salvaOggetto(mockOggettoDTO, mockStoria)).thenReturn("Oggetto salvato con successo");

        ResponseEntity<String> response = oggettoController.salvaOggetto(mockOggettoDTO, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Oggetto salvato con successo");
    }

    @Test
    void testFetchOggetti() {
        Storia mockStoria = new Storia();
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("oggetti", "lista di oggetti");
        when(session.getAttribute("storiaCorrente")).thenReturn(mockStoria);
        //when(oggettoService.fetchOggettiStoria(mockStoria)).thenReturn(mockResponse);

        ResponseEntity<Map<String, Object>> response = oggettiController.fetchOggettiStoria(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }
}