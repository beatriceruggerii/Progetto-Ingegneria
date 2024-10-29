package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.UtenteService;
import OmisFax.OmiStories.Services.UtentiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;

    @GetMapping("/autori")
    public ResponseEntity<Map<String, Object>> fetchAutori(HttpSession session) {
        System.out.println("richiesta di fetch storie ricevuta");
        Map<String, Object> responseData = utentiService.responseFetchAutori();
        return ResponseEntity.ok(responseData);
    }
}
