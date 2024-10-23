package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Controllers.OggettiController;
import OmisFax.OmiStories.Services.OggettoService;
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

public class OggettiControllerTest {

    @InjectMocks
    private OggettiController controllerOggetti;

    @Mock
    private OggettoService servizioOggetto;

    @Mock
    private HttpSession sessione;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOggetti() {
        long idScenario = 1L;
        Map<String, Object> rispostaMock = new HashMap<>();
        rispostaMock.put("oggetti", "lista di oggetti");

        when(servizioOggetto.getOggetti(idScenario)).thenReturn(rispostaMock);

        ResponseEntity<Map<String, Object>> risposta = controllerOggetti.getOggetti(idScenario, sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(rispostaMock);
    }

    @Test
    void testGetOggettiControllori() {
        long idScenario = 1L;
        long idPartita = 2L;
        Map<String, Object> rispostaMock = new HashMap<>();
        rispostaMock.put("oggettiControllori", "lista di oggetti controllori");

        when(sessione.getAttribute("idPartitaInCorso")).thenReturn(idPartita);
        when(servizioOggetto.getOggettiControllori(idScenario, idPartita)).thenReturn(rispostaMock);

        ResponseEntity<Map<String, Object>> risposta = controllerOggetti.getOggettiContollori(idScenario, sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(rispostaMock);
    }
}
