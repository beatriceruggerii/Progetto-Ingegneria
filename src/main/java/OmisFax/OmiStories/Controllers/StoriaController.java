package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.StoriaService;
import OmisFax.OmiStories.Services.StoryDataService;
import OmisFax.OmiStories.Services.interfaces.IStoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/storia")
public class StoriaController {
    private IStoriaService storiaService;
    private StoryDataService storyDataService;
    @Autowired
    public StoriaController(StoriaService storiaService, StoryDataService storyDataService) {
        this.storiaService = storiaService;
        this.storyDataService = storyDataService;
    }


    @PostMapping("/salva")
    public ResponseEntity<String> salvaStoria(@RequestBody StoriaDTO payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        String username = (String) session.getAttribute("loggedUsername");
        Storia storia = storiaService.salvaStoria(payload,username);
        session.setAttribute("storiaCorrente", storia);
        return ResponseEntity.ok("Storia salvata con successo.");
    }

    @GetMapping("/{titoloStoria}")
    public ResponseEntity<Map<String, Object>> fetchDatiStoria(@PathVariable String titoloStoria) {
        System.out.println("richiesta dei dati della storia ricevuti");
        return ResponseEntity.ok(storyDataService.getCompleteStoriaData(titoloStoria));
    }

}