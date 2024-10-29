package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;

import java.util.List;
import java.util.Map;

public interface IScenariService {
    Map<String, Object> fetchScenari(Storia storia);
    List<Scenario> findByStoria(Storia storia);
}

