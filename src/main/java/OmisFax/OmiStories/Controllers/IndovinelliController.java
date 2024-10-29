package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.IndovinelliService;
import OmisFax.OmiStories.Services.IndovinelloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indovinelli")
public class IndovinelliController {
    @Autowired
    private IndovinelliService indovinelliService;

    @GetMapping("/{idScenario}")
    public ResponseEntity<Map<String, Object>> getIndovinelli(@PathVariable long idScenario, HttpSession session) {
        System.out.println("Richiesta di fetch indovinelli ricevuta. Scenario madre: " + idScenario);
        List<Indovinello> indovinelli = indovinelliService.findByScenarioMadre(idScenario);
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("indovinelli", indovinelli);
        System.out.println(responseData.toString()); //debug
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/")
    //metodo che ritorna tutti gli indovinelli della storia in sessione
    public ResponseEntity<Map<String, Object>> fetchIndovinelli(HttpSession session) {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        System.out.println("richiesta di fetch indovinelli ricevuta");
        return ResponseEntity.ok(indovinelliService.responseFetchIndovinelli(storia));
    }
}
