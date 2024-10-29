package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.DTOs.IndovinelloDTO;

public interface IIndovinelloService {
    String responseSalvaIndovinello(IndovinelloDTO infoIndovinello);
    boolean registraIndovinello(Indovinello indovinello);
    boolean modificaIndovinello(Long idIndovinello, IndovinelloDTO nuovoIndovinello);
}

