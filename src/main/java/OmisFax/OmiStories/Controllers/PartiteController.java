package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.Services.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partite")
public class PartiteController {

    @Autowired
    private PartitaService partitaService;

    @GetMapping("/")
    public ResponseEntity<List<PartitaDTO>> getPartite(HttpSession session) {
        String username = (String) session.getAttribute("loggedUsername");
        if (username == null) {
            System.out.println("username: "+username);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<PartitaDTO> partite = partitaService.trovaPartitePerUtente(username);
        System.out.println("partite: "+partite);
        return new ResponseEntity<>(partite, HttpStatus.OK);
    }

    @DeleteMapping("/{idPartita}")
    public ResponseEntity<Void> eliminaPartita(@PathVariable long idPartita) {
        partitaService.deleteById(idPartita);
        return ResponseEntity.noContent().build();
    }

}
