package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping("/aggiungi")
    public ResponseEntity<Inventario> aggiungiOggettoAInventario(
            @RequestParam Long idPartita,
            @RequestParam Long idOggetto) {

        Inventario inventario = inventarioService.aggiungiOggettoAInventario(idPartita, idOggetto);

        return ResponseEntity.ok(inventario);
    }
}

