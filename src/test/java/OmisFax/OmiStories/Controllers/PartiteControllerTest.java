package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.Services.PartiteService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class PartiteControllerTest {

    @InjectMocks
    private PartiteController controllerPartite;

    @Mock
    private PartiteService partiteService;

    @Mock
    private HttpSession sessione;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPartiteUtenteLoggato() {
        String nomeUtente = "utenteDiTest";
        List<PartitaDTO> partiteMock = new ArrayList<>();
        partiteMock.add(new PartitaDTO());

        when(sessione.getAttribute("loggedUsername")).thenReturn(nomeUtente);
        when(partiteService.trovaPartitePerUtente(nomeUtente)).thenReturn(partiteMock);

        ResponseEntity<List<PartitaDTO>> risposta = controllerPartite.getPartite(sessione);
        assert risposta.getStatusCode() == HttpStatus.OK;
        assert risposta.getBody().equals(partiteMock);
    }

    @Test
    void testGetPartiteUtenteNonLoggato() {
        when(sessione.getAttribute("loggedUsername")).thenReturn(null);

        ResponseEntity<List<PartitaDTO>> risposta = controllerPartite.getPartite(sessione);
        assert risposta.getStatusCode() == HttpStatus.UNAUTHORIZED;
    }
}
