package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.ScelteService;
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

public class ScelteControllerTest {

    @InjectMocks
    private ScelteController controllerScelte;

    @Mock
    private ScelteService servizioScelte;

    @Mock
    private HttpSession sessione;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchScelte() {
        Storia storiaMock = new Storia();
        Map<String, Object> rispostaMock = new HashMap<>();
        rispostaMock.put("scelte", "lista di scelte");

        when(sessione.getAttribute("storiaCorrente")).thenReturn(storiaMock);
        when(servizioScelte.responseFetchScelte(storiaMock)).thenReturn(rispostaMock);

        ResponseEntity<Map<String, Object>> risposta = controllerScelte.fetchScelte(sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(rispostaMock);
    }

    @Test
    void testFetchScelteScenario() {
        Long idScenario = 1L;
        Map<String, Object> rispostaMock = new HashMap<>();
        rispostaMock.put("scelteScenario", "lista di scelte dello scenario");

        when(servizioScelte.fetchScelteScenario(idScenario)).thenReturn(rispostaMock);

        ResponseEntity<Map<String, Object>> risposta = controllerScelte.fetchScelteScenario(idScenario, sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(rispostaMock);
    }
}