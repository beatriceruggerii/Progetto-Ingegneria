package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.IndovinelliService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class IndovinelliControllerTest {
    @InjectMocks
    private IndovinelliController controllerIndovinelli;
    @Mock
    private IndovinelliService serviziIndovinello;
    @Mock
    private HttpSession sessione;

    public IndovinelliControllerTest() {
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIndovinelli() {
        long idScenario = 1L;
        List<Indovinello> indovinelliMock = new ArrayList();
        indovinelliMock.add(new Indovinello());
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("indovinelli", indovinelliMock);
        Mockito.when(this.serviziIndovinello.findByScenarioMadre(idScenario)).thenReturn(indovinelliMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerIndovinelli.getIndovinelli(idScenario, this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map)risposta.getBody()).equals(rispostaMock);

    }

    @Test
    void testFetchIndovinelli() {
        Storia storiaMock = new Storia();
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("indovinelli", "testIndovinelli");
        Mockito.when(this.sessione.getAttribute("storiaCorrente")).thenReturn(storiaMock);
        Mockito.when(this.serviziIndovinello.responseFetchIndovinelli(storiaMock)).thenReturn(rispostaMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerIndovinelli.fetchIndovinelli(this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map)risposta.getBody()).equals(rispostaMock);

    }
}

