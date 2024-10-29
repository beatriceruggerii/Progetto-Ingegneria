package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Storia;

import java.util.List;
import java.util.Map;

public interface IScelteService {
    Map<String, Object> responseFetchScelte(Storia storia);
    List<Scelta> findByStoria(Storia storia);
    Map<String, Object> fetchScelteScenario(Long idScenario);
}

