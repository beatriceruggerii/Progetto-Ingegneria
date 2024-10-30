package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Services.InventarioService;
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
public class InventarioControllerTest {
    @InjectMocks
    private InventarioController controllerInventario;
    @Mock
    private InventarioService servizioInventario;
    @Mock
    private HttpSession sessione;

    public InventarioControllerTest() {
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAggiungiOggettoAInventario() {
        long idPartita = 1L;
        long idOggetto = 2L;
        Inventario inventarioMock = new Inventario();
        Map<String, Long> requestBody = new HashMap();
        requestBody.put("idOggetto", idOggetto);
        Mockito.when(this.sessione.getAttribute("idPartitaInCorso")).thenReturn(idPartita);
        Mockito.when(this.servizioInventario.aggiungiOggettoAInventario(idPartita, idOggetto)).thenReturn(inventarioMock);
        ResponseEntity<Inventario> risposta = this.controllerInventario.aggiungiOggettoAInventario(requestBody, this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Inventario)risposta.getBody()).equals(inventarioMock);

    }

    @Test
    void testGetInventario() {
        long idPartita = 1L;
        List<Inventario> inventarioMock = new ArrayList();
        inventarioMock.add(new Inventario());
        Map<String, Object> rispostaMock = new HashMap();
        rispostaMock.put("inventario", inventarioMock);
        Mockito.when(this.sessione.getAttribute("idPartitaInCorso")).thenReturn(idPartita);
        Mockito.when(this.servizioInventario.getInventarioPartita(idPartita)).thenReturn(inventarioMock);
        ResponseEntity<Map<String, Object>> risposta = this.controllerInventario.getInventario(this.sessione);

        assert risposta.getStatusCode() == HttpStatus.OK;

        assert ((Map)risposta.getBody()).equals(rispostaMock);

    }
}
