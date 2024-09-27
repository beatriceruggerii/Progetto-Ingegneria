package OmisFax.OmiStories.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OmiStoriesController {
    @GetMapping("/index")
    String Login() {
        return "index";
    }
    @GetMapping("/Register")
    String Register() {
        return "registration";
    }
    @GetMapping("/homepage")
    String Homepage() {
        return "Homepage";
    }
    @GetMapping("/LeMiePartite")
    String LeMiePartite() {
        return "LeMiePartite";
    }
    @GetMapping("/Premium")
    String Premium() {
        return "Premium";
    }

}