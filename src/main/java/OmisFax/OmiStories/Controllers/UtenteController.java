package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/fetch_autori")
    public ResponseEntity<Map<String, Object>> fetchAutori(HttpSession session) {
        System.out.println("richiesta di fetch storie ricevuta");
        return utenteService.responseFetchAutori(session);
    }
}
