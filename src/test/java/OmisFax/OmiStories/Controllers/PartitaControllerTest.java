package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Controllers.PartitaController;
import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Services.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PartitaControllerTest {

    @InjectMocks
    private PartitaController partitaController;

    @Mock
    private PartitaService partitaService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvaPartitaSuccess() {
        String titoloStoria = "Storia di prova";
        String username = "testUser";
        Partita mockPartita = new Partita();
        mockPartita.setId(1L);

        when(session.getAttribute("loggedUsername")).thenReturn(username);
        when(partitaService.salvaPartita(titoloStoria, username)).thenReturn(mockPartita);

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        verify(session).setAttribute("idPartitaInCorso", mockPartita.getId());
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Partita salvata con successo");
    }

    @Test
    void testSalvaPartitaUserNotLogged() {
        String titoloStoria = "Storia di prova";
        when(session.getAttribute("loggedUsername")).thenReturn(null);

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert response.getBody().equals("Errore durante il salvataggio della partita");
    }

    @Test
    void testSalvaPartitaServiceThrowsException() {
        String titoloStoria = "Storia di prova";
        String username = "testUser";

        when(session.getAttribute("loggedUsername")).thenReturn(username);
        when(partitaService.salvaPartita(titoloStoria, username)).thenThrow(new RuntimeException("Errore di salvataggio"));

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        assert response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert response.getBody().equals("Errore durante il salvataggio della partita");
    }
}
