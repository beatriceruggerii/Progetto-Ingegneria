package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private StoriaRepository storiaRepository;

    private boolean trovaUsername(String uname) {
        Utente utente = utenteRepository.findByUsername(uname);
        return utente != null;
    }

    public Utente trovaUtente(String username){
        Utente utente = null;
        utente = utenteRepository.findByUsername(username);
        return utente;
    }

    public boolean autentica(String usernameOrEmail, String password) {
        Utente utente = null;
        utente = utenteRepository.findByUsernameAndPassword(usernameOrEmail, password);
        return utente != null;
    }

    public boolean registraUtente(Utente utente) {
        if (trovaUsername(utente.getUsername())) {
            return false;
        }
        try {
            utenteRepository.save(utente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void upgradePremium(String username){
        System.out.println(username);
        Utente utente = utenteRepository.findByUsername(username);
        if(utente != null){
            utente.toPremium();
            utenteRepository.save(utente);
        }
    }

    public Map<String, Object> responseFetchAutori() {
        Map<String, Object> responseData = new HashMap<>();
        List<Utente> listaAutori = listaAutori();
        if(listaAutori.isEmpty()){
            System.out.println("Autori non trovati");
        }
        System.out.println("Autori trovati: "+ listaAutori.size());
        for(int i = 0; i<listaAutori.size(); i++){
            System.out.println(listaAutori.get(i).toString());
        }
        responseData.put("listaAutori", listaAutori);
        return responseData;
    }

    public List<Utente> listaAutori(){
        List<Storia> listaStorie = storiaRepository.findAll();
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