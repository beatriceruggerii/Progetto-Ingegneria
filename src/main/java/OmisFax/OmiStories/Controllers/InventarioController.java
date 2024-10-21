package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Services.InventarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping("/aggiungi")
    // @param id: id dell'oggetto passato in post
    public ResponseEntity<Inventario> aggiungiOggettoAInventario(
            @RequestBody Map<String, Long> requestBody, HttpSession session) {
        System.out.println("Richiesta di salva oggetto ricevuta"); //debug
        System.out.println("idPartita in corso: " + session.getAttribute("idPartitaInCorso"));
        long idPartita = (long) session.getAttribute("idPartitaInCorso");

        System.out.println("idPartita: " + idPartita);

        long idOggetto = requestBody.get("idOggetto");

        System.out.println("idOggetto: " + idOggetto);
        Inventario inventario = inventarioService.aggiungiOggettoAInventario(idPartita, idOggetto);

        return ResponseEntity.ok(inventario);
    }
}

