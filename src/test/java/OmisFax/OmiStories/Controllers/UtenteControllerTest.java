package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Services.UtenteService;
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

public class UtenteControllerTest {

    @InjectMocks
    private UtenteController utenteController;

    @Mock
    private UtenteService utenteService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAutori() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("author", "testAuthor");

        when(utenteService.responseFetchAutori(session)).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<Map<String, Object>> response = utenteController.fetchAutori(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(mockResponse);
    }
}