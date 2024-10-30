package OmisFax.OmiStories.Controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.SceltaDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Services.SceltaService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SceltaControllerTest {

    @InjectMocks
    private SceltaController sceltaController;

    @Mock
    private IScenarioService scenarioService;

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
        // Configura un DTO di esempio
        SceltaDTO mockSceltaDTO = new SceltaDTO();
        mockSceltaDTO.setTesto("Testo scelta");
        mockSceltaDTO.setIdMadre(1L);
        mockSceltaDTO.setIdFiglio(2L);

        // Mock delle dipendenze
        Scenario scenarioMadre = new Scenario(); // Crea scenario madre simulato
        Scenario scenarioFiglio = new Scenario(); // Crea scenario figlio simulato
        Scelta mockScelta = new Scelta(); // Crea un oggetto Scelta simulato

        // Configura i mock per i metodi chiamati
        when(scenarioService.findById(1L)).thenReturn(scenarioMadre);
        when(scenarioService.findById(2L)).thenReturn(scenarioFiglio);
        when(sceltaService.responseSalvaScelta(mockSceltaDTO)).thenReturn(mockScelta);

        // Chiama il metodo da testare
        sceltaController.salvaScelta(mockSceltaDTO, session);

        // Verifica che il metodo responseSalvaScelta sia stato chiamato con il DTO simulato
        verify(sceltaService).responseSalvaScelta(mockSceltaDTO);

        // Verifica che la scelta sia stata salvata nella sessione
        verify(session).setAttribute("sceltaCorrente", mockScelta);

        // Verifica che non siano state lanciate eccezioni non gestite
        assertDoesNotThrow(() -> sceltaController.salvaScelta(mockSceltaDTO, session));
    }



    @Test
    void testModificaSceltaSuccess() {
        Long idScelta = 1L;
        SceltaDTO nuovaScelta = new SceltaDTO();
        when(sceltaService.modificaScelta(nuovaScelta)).thenReturn(true);

        ResponseEntity<String> response = sceltaController.modificaScelta(idScelta, nuovaScelta);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Modifica avvenuta con successo.");
    }

    @Test
    void testModificaSceltaFailure() {
        Long idScelta = 1L;
        SceltaDTO nuovaScelta = new SceltaDTO();
        when(sceltaService.modificaScelta(nuovaScelta)).thenReturn(false);

        ResponseEntity<String> response = sceltaController.modificaScelta(idScelta, nuovaScelta);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
        assert response.getBody().equals("Modifica fallita: Scelta non trovata.");
    }
}