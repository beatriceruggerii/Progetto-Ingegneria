package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Storia;

import java.util.List;
import java.util.Map;

public interface IOggettiService {
    Map<String, Object> fetchOggettiStoria(Storia storia);
    List<Oggetto> findByStoria(Storia storia);
    Map<String, Object> getOggetti(long idScenario);
    Map<String, Object> getOggettiControllori(long idScenario, long idPartita);
}

