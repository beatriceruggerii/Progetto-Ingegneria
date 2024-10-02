package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoriaFactory {
    @Autowired
    private UtenteService utenteService;

    public Storia createStoria(String titolo, String username){
        Utente utente = utenteService.trovaUtente(username);
        if(utente==null){
            throw new IllegalArgumentException("Utente non valido, rieffettua l'accesso.");
        }
        return new Storia(titolo,utente);
    }

}
