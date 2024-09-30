package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Storia;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/session_data")
    public ResponseEntity<Map<String, Object>> getSessionData(HttpSession session) {
        System.out.println("richiesta dati ricevuta");
        String username = (String) session.getAttribute("loggedUsername");
        Boolean isPremium = (boolean) session.getAttribute("isPremium");
        String storyTitle = "";
        if(session.getAttribute("storiaCorrente") != null) {
            Storia storiaCorrente = (Storia) session.getAttribute("storiaCorrente");
            storyTitle = storiaCorrente.getTitolo();
        }

        System.out.println(username + storyTitle + isPremium);

        // mappa di risposta
        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("username", username);
        sessionData.put("storyTitle", storyTitle);
        sessionData.put("isPremium", isPremium);

        //debug:
        System.out.println(sessionData.get("loggedUsername"));

        return ResponseEntity.ok(sessionData);
    }
}

