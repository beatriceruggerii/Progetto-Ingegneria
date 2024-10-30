package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import OmisFax.OmiStories.Services.interfaces.IUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UtenteService implements IUtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

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


    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
}