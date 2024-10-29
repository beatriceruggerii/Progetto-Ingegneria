package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Storia;

import java.util.List;
import java.util.Map;

public interface IIndovinelliService {
    Map<String, Object> responseFetchIndovinelli(Storia storia);
    List<Indovinello> findByStoria(Storia storia);
    List<Indovinello> findByScenarioMadre(long idScenario);
}
