package OmisFax.OmiStories.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping("/security")
    public ResponseEntity<String> logControl(HttpSession session){
        if(session.getAttribute("loggedUsername") == null){
            return new ResponseEntity<>("unauthorized", HttpStatus.UNAUTHORIZED);
        }
        else{
            return new ResponseEntity<>("logged", HttpStatus.OK);
        }
    }
}
