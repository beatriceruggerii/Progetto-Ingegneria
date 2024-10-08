package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Services.StoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class StoriaController {
    private final StoriaService storiaService;
    @Autowired
    public StoriaController(StoriaService storiaService) {
        this.storiaService = storiaService;
    }


    @PostMapping("/salva_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody StoriaDTO payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        return storiaService.salvaStoria(payload, session);
    }

    @GetMapping("/fetch_storie")
    public ResponseEntity<Map<String, Object>> fetchStorie(HttpSession session) {
        System.out.println("richiesta di fetch storie ricevuta");
        return storiaService.responseFetchStorie(session);
    }

    @PostMapping("/filtro-autore")
    public ResponseEntity<Map<String, Object>> filtroAutore(@RequestBody String username, HttpSession session) {
        System.out.println("richiesta di filtro Autore");
        return storiaService.responseFiltroAutore(username, session);
    }

    @PostMapping("/filtro-titolo")
    public ResponseEntity<Map<String, Object>> filtroRicerca(@RequestBody String titolo, HttpSession session){
        System.out.println("richiesta di filtro Titolo");
        return storiaService.responseFiltroTitolo(titolo, session);
    }



}
