package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.UtentiService;
import OmisFax.OmiStories.Services.interfaces.IUtentiService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class UtentiControllerTest {
    @InjectMocks
    private UtentiController controllerUtenti;
    @Mock
    private UtentiService utentiService;
    @Mock
    private HttpSession sessione;

    public UtentiControllerTest() {
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAutori() {
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("autori", "testAutori");
        Mockito.when(this.utentiService.responseFetchAutori()).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerUtenti.fetchAutori(this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map)risposta.getBody()).equals(rispostaMock);

    }
}
