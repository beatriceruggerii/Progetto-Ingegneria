package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Utente;

public interface IUtenteService {
    Utente trovaUtente(String username);
    boolean autentica(String usernameOrEmail, String password);
    boolean registraUtente(Utente utente);
    void upgradePremium(String username);
    Utente findByUsername(String username);
}

