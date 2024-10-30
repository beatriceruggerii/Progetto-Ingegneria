package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.StorieService;
import OmisFax.OmiStories.Services.interfaces.IStorieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/storie")
public class StorieController {
    private IStorieService storieService;
    @Autowired
    public StorieController(StorieService storieService) {
        this.storieService = storieService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> fetchStorie() {
        System.out.println("richiesta di fetch storie ricevuta");
        Map<String,Object> responseData = storieService.responseFetchStorie();
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/autore/{username}")
    public ResponseEntity<Map<String, Object>> filtroAutore(@PathVariable String username) {
        System.out.println("richiesta di filtro Autore");
        Map<String,Object> respondeData = storieService.responseStorieAutore(username);
        return ResponseEntity.ok(respondeData);
    }

    @GetMapping("/autoreInSessione")
    public ResponseEntity<Map<String, Object>> filtroAutoreInSessione(HttpSession session) {
        System.out.println("richiesta di filtro Autore");
        String username = (String)session.getAttribute("loggedUsername");
        Map<String,Object> respondeData = storieService.responseStorieAutore(username);
        return ResponseEntity.ok(respondeData);
    }

    @GetMapping("/titolo/{titolo}")
    public ResponseEntity<Map<String, Object>> filtroRicerca(@PathVariable String titolo, HttpSession session){
        System.out.println("richiesta di filtro Titolo");
        return ResponseEntity.ok(storieService.responseFiltroTitolo(titolo));
    }

}
