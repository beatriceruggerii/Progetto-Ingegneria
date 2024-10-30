package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.ScenariService;
import OmisFax.OmiStories.Services.interfaces.IScenariService;
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

    private IScenariService scenariService;

    @Autowired
    public ScenariController(ScenariService scenariService) {
        this.scenariService = scenariService;
    }

    @GetMapping("/")
    //restituisce tutti gli scenari della storia corrente
    public ResponseEntity<Map<String, Object>> fetchScenari(HttpSession session) {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        System.out.println("richiesta di fetch scenari ricevuta");
        Map<String, Object> responseData = scenariService.fetchScenari(storia);
        return ResponseEntity.ok(responseData);
    }
}
