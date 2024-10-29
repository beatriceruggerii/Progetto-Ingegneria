package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Utente;

import java.util.List;
import java.util.Map;

public interface IUtentiService {
    Map<String, Object> responseFetchAutori();
    List<Utente> listaAutori();
}


