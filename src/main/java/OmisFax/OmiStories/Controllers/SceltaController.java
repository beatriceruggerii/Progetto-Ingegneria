package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.SceltaService;
import OmisFax.OmiStories.Services.ScenarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SceltaController {

    private final SceltaService sceltaService;

    @Autowired
    public SceltaController(SceltaService sceltaService) {
        this.sceltaService = sceltaService;
    }

    @GetMapping("/fetch_scelte")
    public ResponseEntity<Map<String, Object>> fetchScelte(HttpSession session) {
        System.out.println("richiesta di fetch scelte ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            System.out.println("Storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scelta> listaScelte = sceltaService.listaScelte(storia);
        if(listaScelte.isEmpty()){
            System.out.println("Scelte non trovate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        System.out.println("scelte trovate: "+ listaScelte.size());
        for(int i = 0; i<listaScelte.size(); i++){
            System.out.println(listaScelte.get(i).toString());
        }
        responseData.put("listaScelte", listaScelte);
        return ResponseEntity.ok(responseData);
    }
}
