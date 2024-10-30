package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.StorieService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StorieControllerTest {
    @InjectMocks
    private StorieController controllerStorie;
    @Mock
    private StorieService servizioStorie;
    @Mock
    private HttpSession sessione;

    public StorieControllerTest() {
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchStorie() {
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("storie", "testStorie");
        Mockito.when(this.servizioStorie.responseFetchStorie()).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerStorie.fetchStorie();

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map) risposta.getBody()).equals(rispostaMock);

    }

    @Test
    void testFiltroAutore() {
        String username = "testUser";
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("storieAutore", "testStorieAutore");
        Mockito.when(this.servizioStorie.responseStorieAutore(username)).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerStorie.filtroAutore(username);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map) risposta.getBody()).equals(rispostaMock);

    }

    @Test
    void testFiltroAutoreInSessione() {
        String username = "testUser";
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("storieAutore", "testStorieAutore");
        Mockito.when(this.sessione.getAttribute("loggedUsername")).thenReturn(username);
        Mockito.when(this.servizioStorie.responseStorieAutore(username)).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerStorie.filtroAutoreInSessione(this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map) risposta.getBody()).equals(rispostaMock);

    }

    @Test
    void testFiltroRicerca() {
        String titolo = "testTitolo";
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("storieTitolo", "testStorieTitolo");
        Mockito.when(this.servizioStorie.responseFiltroTitolo(titolo)).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerStorie.filtroRicerca(titolo, this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map) risposta.getBody()).equals(rispostaMock);

    }

}