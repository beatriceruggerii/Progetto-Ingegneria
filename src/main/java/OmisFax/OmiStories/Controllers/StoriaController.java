package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Services.StoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/fetch_storie_utente")
    public ResponseEntity<Map<String, Object>> fetchStorieUtente(HttpSession session) {
        System.out.println("richiesta delle storie dell'autore");
        return storiaService.responseStorieAutore(session);
    }


    //TODO: metodo che ritorna troppi dati inutili?
    @GetMapping("/fetch_dati_storia/{titolo}")
    public ResponseEntity<Map<String, Object>> fetchDatiStoria(@PathVariable String titolo, HttpSession session) {
        System.out.println("richiesta dei dati della storia ricevuti");
        return storiaService.responseDatiStoria(titolo, session);
    }

    @GetMapping("/fetch_scenario_iniziale/{titolo}")
    public ResponseEntity<Map<String, Object>> fetchScenarioIniziale(@PathVariable String titolo, HttpSession session) {
        System.out.println("richiesta scenario Iniziale ricevutw");
        return storiaService.responseScenarioIniziale(titolo, session);
    }





}