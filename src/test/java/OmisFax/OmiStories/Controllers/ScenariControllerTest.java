package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.ScenariService;
import OmisFax.OmiStories.Services.interfaces.IScenariService;
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

public class ScenariControllerTest {

    @InjectMocks
    private ScenariController controllerScenari;

    @Mock
    private ScenariService servizioScenari;

    @Mock
    private HttpSession sessione;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchScenari() {
        Storia storiaMock = new Storia();
        Map<String, Object> rispostaMock = new HashMap<>();
        rispostaMock.put("scenari", "lista di scenari");

        when(sessione.getAttribute("storiaCorrente")).thenReturn(storiaMock);
        when(servizioScenari.fetchScenari(storiaMock)).thenReturn(rispostaMock);

        ResponseEntity<Map<String, Object>> risposta = controllerScenari.fetchScenari(sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(rispostaMock);
    }
}
