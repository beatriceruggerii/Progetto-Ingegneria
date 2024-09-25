package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.UtenteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {
    @Autowired
    private UtenteService utenteService;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/proxy/pay")
    public ResponseEntity<String> proxyPay(@RequestBody String paymentData, HttpSession session) {
        String targetUrl = "http://localhost:6789/pay";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(paymentData, headers);

        // Effettua la richiesta POST al server su localhost:6789
        ResponseEntity<String> response = restTemplate.exchange(targetUrl, HttpMethod.POST, request, String.class);

        // Modifica il boolean 'premium' dell'utente
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode body = objectMapper.readTree(response.getBody());
            String status = body.get("status").asText();
            if(status.trim().equals("Autorizzato")){
                utenteService.upgradePremium(session.getAttribute("loggedUsername").toString());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}