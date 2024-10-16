import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import OmisFax.OmiStories.Controllers.LoginController;
import OmisFax.OmiStories.Entities.Utente;

public class LoginControllerTest {

    @Mock
    private LoginController loginController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginController() {
        Utente mockUtente = new Utente();
        mockUtente.setUsername("testUser");
        mockUtente.setPassword("testPassword");
        HttpSession mockSession = mock(HttpSession.class);

        when(loginController.login(mockUtente, mockSession)).thenReturn(new ResponseEntity<>("Login Successful", HttpStatus.OK));

        ResponseEntity<String> response = loginController.login(mockUtente, mockSession);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Login Successful");
    }

    @Test
    void testLoginControllerInvalidCredentials() {
        Utente mockUtente = new Utente();
        mockUtente.setUsername("invalidUser");
        mockUtente.setPassword("invalidPassword");
        HttpSession mockSession = mock(HttpSession.class);

        when(loginController.login(mockUtente, mockSession)).thenReturn(new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED));

        ResponseEntity<String> response = loginController.login(mockUtente, mockSession);
        assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;
        assert response.getBody().equals("Invalid Credentials");
    }
}