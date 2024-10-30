package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Services.interfaces.IPartitaService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PartitaControllerTest {

    @InjectMocks
    private PartitaController partitaController;

    @Mock
    private IPartitaService partitaService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRiprendiPartitaSuccess() {
        long idPartita = 1L;
        Partita partitaMock = new Partita();

        when(partitaService.getPartita(idPartita)).thenReturn(partitaMock);

        ResponseEntity<String> response = partitaController.riprendiPartita(idPartita, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Partita ripresa con successo", response.getBody());
        verify(session).setAttribute("idPartitaInCorso", idPartita);
    }

    @Test
    void testRiprendiPartitaNotFound() {
        long idPartita = 1L;

        when(partitaService.getPartita(idPartita)).thenReturn(null);

        ResponseEntity<String> response = partitaController.riprendiPartita(idPartita, session);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Partita non trovata", response.getBody());
    }

    @Test
    void testSalvaPartitaSuccess() {
        String username = "testUser";
        String titoloStoria = "Storia di prova";
        Partita partitaMock = new Partita();
        partitaMock.setId(1L);

        when(session.getAttribute("loggedUsername")).thenReturn(username);
        when(partitaService.salvaPartita(titoloStoria.trim(), username)).thenReturn(partitaMock);

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Partita salvata con successo", response.getBody());
        verify(session).setAttribute("idPartitaInCorso", partitaMock.getId());
    }

    @Test
    void testSalvaPartitaUtenteNonLoggato() {
        String titoloStoria = "Storia di prova";

        when(session.getAttribute("loggedUsername")).thenReturn(null);

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Utente non loggato.", response.getBody());
    }

    @Test
    void testSalvaPartitaErroreSalvataggio() {
        String username = "testUser";
        String titoloStoria = "Storia di prova";

        when(session.getAttribute("loggedUsername")).thenReturn(username);
        when(partitaService.salvaPartita(titoloStoria.trim(), username)).thenThrow(new RuntimeException());

        ResponseEntity<String> response = partitaController.salvaPartita(titoloStoria, session);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Errore durante il salvataggio della partita", response.getBody());
    }

    @Test
    void testAggiornaPartitaSuccess() {
        long idScenarioFiglio = 2L;
        long idPartitaCorrente = 1L;

        when(session.getAttribute("idPartitaInCorso")).thenReturn(idPartitaCorrente);
        when(partitaService.aggiornaPartita(idPartitaCorrente, idScenarioFiglio)).thenReturn(true);

        ResponseEntity<String> response = partitaController.aggiornaPartita(String.valueOf(idScenarioFiglio), session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Partita aggiornata", response.getBody());
    }

    @Test
    void testAggiornaPartitaFailure() {
        long idScenarioFiglio = 2L;
        long idPartitaCorrente = 1L;

        when(session.getAttribute("idPartitaInCorso")).thenReturn(idPartitaCorrente);
        when(partitaService.aggiornaPartita(idPartitaCorrente, idScenarioFiglio)).thenReturn(false);

        ResponseEntity<String> response = partitaController.aggiornaPartita(String.valueOf(idScenarioFiglio), session);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Non è stato possibile aggiornare la partita", response.getBody());
    }

    @Test
    void testAggiornaPartitaScenarioNonValido() {
        String idScenarioFiglio = "nonNumerico";

        ResponseEntity<String> response = partitaController.aggiornaPartita(idScenarioFiglio, session);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ID scenario non valido", response.getBody());
    }

    @Test
    void testEliminaPartitaSuccess() {
        long idPartita = 1L;

        ResponseEntity<Void> response = partitaController.eliminaPartita(idPartita);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(partitaService).deleteById(idPartita);
    }

    @Test
    void testEliminaPartitaFailure() {
        long idPartita = 1L;

        doThrow(new RuntimeException()).when(partitaService).deleteById(idPartita);

        ResponseEntity<Void> response = partitaController.eliminaPartita(idPartita);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
