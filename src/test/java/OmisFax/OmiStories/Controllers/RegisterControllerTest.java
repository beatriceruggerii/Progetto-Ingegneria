package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import OmisFax.OmiStories.Entities.Utente;

public class RegisterControllerTest {

    @Mock
    private RegisterController registerController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterController() {
        Utente mockUtente = new Utente();
        mockUtente.setUsername("newUser");
        mockUtente.setPassword("newPassword");
        HttpSession mockSession = mock(HttpSession.class);

        when(registerController.register(mockUtente, mockSession)).thenReturn(new ResponseEntity<>("Registration Successful", HttpStatus.OK));

        ResponseEntity<String> response = registerController.register(mockUtente, mockSession);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Registration Successful");
    }

    @Test
    void testRegisterControllerUserAlreadyExists() {
        Utente mockUtente = new Utente();
        mockUtente.setUsername("existingUser");
        mockUtente.setPassword("password");
        HttpSession mockSession = mock(HttpSession.class);

        when(registerController.register(mockUtente, mockSession)).thenReturn(new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT));

        ResponseEntity<String> response = registerController.register(mockUtente, mockSession);
        assert response.getStatusCode() == HttpStatus.CONFLICT;
        assert response.getBody().equals("User Already Exists");
    }
}