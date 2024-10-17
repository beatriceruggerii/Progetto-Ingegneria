package OmisFax.OmiStories.Controllers;

import static org.mockito.Mockito.*;

import OmisFax.OmiStories.Entities.Storia;
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

public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogout() {
        doNothing().when(session).invalidate();

        ResponseEntity<String> response = sessionController.logout(session);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Logout successful");
    }

    @Test
    void testGetSessionData() {
        when(session.getAttribute("loggedUsername")).thenReturn("testUser");
        when(session.getAttribute("isPremium")).thenReturn(true);
        Storia mockStoria = new Storia();
        mockStoria.setTitolo("Test Story");
        when(session.getAttribute("storiaCorrente")).thenReturn(mockStoria);

        ResponseEntity<Map<String, Object>> response = sessionController.getSessionData(session);
        assert response.getStatusCode() == HttpStatus.OK;

        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("username", "testUser");
        expectedData.put("storyTitle", "Test Story");
        expectedData.put("isPremium", true);

        assert response.getBody().equals(expectedData);
    }

    @Test
    void testGetSessionDataWithoutStory() {
        when(session.getAttribute("loggedUsername")).thenReturn("testUser");
        when(session.getAttribute("isPremium")).thenReturn(false);
        when(session.getAttribute("storiaCorrente")).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = sessionController.getSessionData(session);
        assert response.getStatusCode() == HttpStatus.OK;

        Map<String, Object> expectedData = new HashMap<>();
        expectedData.put("username", "testUser");
        expectedData.put("storyTitle", "");
        expectedData.put("isPremium", false);

        assert response.getBody().equals(expectedData);
    }
}
