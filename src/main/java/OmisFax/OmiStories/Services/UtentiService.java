package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.interfaces.IStoriaService;
import OmisFax.OmiStories.Services.interfaces.IStorieService;
import OmisFax.OmiStories.Services.interfaces.IUtenteService;
import OmisFax.OmiStories.Services.interfaces.IUtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtentiService implements IUtentiService {
    @Autowired
    private IStorieService storieService;

    public Map<String, Object> responseFetchAutori() {
        Map<String, Object> responseData = new HashMap<>();
        List<Utente> listaAutori = listaAutori();
        if(listaAutori.isEmpty()){
            System.out.println("Autori non trovati");
        }
        System.out.println("Autori trovati: "+ listaAutori.size());
        for (Utente utente : listaAutori) {
            System.out.println(utente.toString());
        }
        responseData.put("listaAutori", listaAutori);
        return responseData;
    }

    public List<Utente> listaAutori(){
        List<Storia> listaStorie = storieService.findAll();
        List<Utente> listaAutori = new ArrayList<>();

        if (listaStorie != null) {  // Controlla che la lista delle storie non sia null
            for (Storia storia : listaStorie) {
                Utente autore = storia.getAutore();  // Recupera l'autore della storia
                // Aggiungi l'autore solo se non è già presente nella lista
                if (autore != null && !listaAutori.contains(autore)) {
                    listaAutori.add(autore);
                }
            }
        }

        return listaAutori;
    }
}
