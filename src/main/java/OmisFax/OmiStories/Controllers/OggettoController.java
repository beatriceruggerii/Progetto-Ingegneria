package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.OggettoDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.interfaces.IOggettoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oggetto")
public class OggettoController {
    private IOggettoService oggettoService;

    @Autowired
    public OggettoController(IOggettoService oggettoService) {
        this.oggettoService = oggettoService;
    }


    @PostMapping("/salva")
    public ResponseEntity<String> salvaOggetto(@RequestBody OggettoDTO payload, HttpSession session) {
        System.out.println("----\n richiesta di salvataggio oggeetto ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        String message = oggettoService.salvaOggetto(payload, storia);
        return ResponseEntity.ok(message);
    }

}