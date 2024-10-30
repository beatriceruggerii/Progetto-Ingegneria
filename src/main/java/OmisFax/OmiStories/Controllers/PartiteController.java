package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.Services.interfaces.IPartiteService;
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
    private IPartiteService partiteService;

    @GetMapping("/")
    public ResponseEntity<List<PartitaDTO>> getPartite(HttpSession session) {
        String username = (String) session.getAttribute("loggedUsername");
        if (username == null) {
            System.out.println("username: "+username);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<PartitaDTO> partite = partiteService.trovaPartitePerUtente(username);
        System.out.println("partite: "+partite);
        return new ResponseEntity<>(partite, HttpStatus.OK);
    }


}
