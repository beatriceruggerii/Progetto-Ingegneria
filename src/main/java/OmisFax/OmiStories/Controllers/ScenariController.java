package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Services.ScenarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/scenari")
public class ScenariController {

    private ScenarioService scenarioService;

    @Autowired
    public ScenariController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/")
    //restituisce tutti gli scenari della storia corrente
    public ResponseEntity<Map<String, Object>> fetchScenari(HttpSession session) {
        System.out.println("richiesta di fetch scenari ricevuta");
        return scenarioService.fetchScenari(session);
    }
}
