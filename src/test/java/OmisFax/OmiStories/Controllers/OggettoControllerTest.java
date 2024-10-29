package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.OggettoDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.OggettoService;
import OmisFax.OmiStories.Services.ScenarioService;
import OmisFax.OmiStories.Services.interfaces.IOggettoService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
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
public class OggettoControllerTest {

    @InjectMocks
    private OggettoController oggettoController;

    @Mock
    private OggettoService oggettoService;

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

}