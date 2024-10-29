package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.DTOs.SceltaDTO;

public interface ISceltaService {
    Scelta responseSalvaScelta(SceltaDTO infoScelta);
    boolean registraScelta(Scelta scelta);
    boolean modificaScelta(SceltaDTO nuovaScelta);
}

