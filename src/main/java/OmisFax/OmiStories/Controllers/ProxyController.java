package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.UtenteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProxyController {
    @Autowired
    private UtenteService utenteService;
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/proxy/pay")
    public ResponseEntity<String> proxyPay(@RequestBody String paymentData, HttpSession session) {
        //url raggiungibile quando il jar è eseguito manualmente
        //String targetUrl = "http://localhost:6789/pay";

        // URL raggiungibile quando il JAR è eseguito dal Docker
        String targetUrl = "http://payment:6789/pay";

        //debug
        System.out.println("Richiesta id pagamento ricevuta. Reindirizzamento a: " + targetUrl);
        System.out.println("Dati di pagamento inviati: " + paymentData);

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
            if (status.trim().equals("Autorizzato")) {
                System.out.println("Stato autorizzato ricevuto. Aggiornamento utente a premium.");
                utenteService.upgradePremium(session.getAttribute("loggedUsername").toString());
                session.setAttribute("isPremium", true);
            }
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Errore di elaborazione JSON\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Si è verificato un errore interno durante il pagamento\"}");
        }
    }

    // Gestione delle eccezioni per la validazione direttamente nel controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "400");
        responseBody.put("message", "Errore di validazione");
        responseBody.put("errors", errors);

        return ResponseEntity.badRequest().body(responseBody);
    }
}