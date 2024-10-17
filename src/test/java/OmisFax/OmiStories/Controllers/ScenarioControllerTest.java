package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.ScenarioDTO;
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

public class ScenarioControllerTest {

    @InjectMocks
    private ScenarioController scenarioController;

    @Mock
    private ScenarioService scenarioService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaScenario() {
        ScenarioDTO mockScenarioDTO = new ScenarioDTO();
        when(scenarioService.salvaScenario(mockScenarioDTO, session)).thenReturn(new ResponseEntity<>("Scenario salvato con successo", HttpStatus.OK));

        ResponseEntity<String> response = scenarioController.salvaScenario(mockScenarioDTO, session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Scenario salvato con successo");
    }

    @Test
    void testFetchScenari() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("scenari", "lista di scenari");
        when(scenarioService.fetchScenari(session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = scenarioController.fetchScenari(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }

    @Test
    void testModificaIndovinelloSuccess() {
        Long idScenario = 1L;
        ScenarioDTO nuovoScenario = new ScenarioDTO();
        when(scenarioService.modificaScenario(idScenario, nuovoScenario)).thenReturn(true);

        ResponseEntity<String> response = scenarioController.modificaIndovinello(idScenario, nuovoScenario);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Modifica avvenuta con successo.");
    }

    @Test
    void testModificaIndovinelloFailure() {
        Long idScenario = 1L;
        ScenarioDTO nuovoScenario = new ScenarioDTO();
        when(scenarioService.modificaScenario(idScenario, nuovoScenario)).thenReturn(false);

        ResponseEntity<String> response = scenarioController.modificaIndovinello(idScenario, nuovoScenario);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody().equals("Modifica fallita: Scenario non trovato.");
    }
}
