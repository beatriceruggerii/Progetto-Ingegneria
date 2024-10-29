package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoryDataService {

    @Autowired
    private IScenariService scenariService;

    @Autowired
    private IScelteService scelteService;

    @Autowired
    private IIndovinelliService indovinelliService;

    @Autowired
    private IOggettiService oggettiService;

    @Autowired
    private IStoriaService storiaService;

    public Map<String, Object> getCompleteStoriaData(String titolo) {
        Map<String, Object> responseData = new HashMap<>();

        // Oggetto storia
        Storia storia = storiaService.findStoriaByTitolo(titolo);
        responseData.put("storia", storia);

        // Scenari
        List<Scenario> scenari = scenariService.findByStoria(storia);
        responseData.put("scenari", scenari);

        // Scelte
        List<Scelta> scelte = scelteService.findByStoria(storia);
        responseData.put("scelte", scelte);

        // Indovinelli
        List<Indovinello> indovinelli = indovinelliService.findByStoria(storia);
        responseData.put("indovinelli", indovinelli);

        // Oggetti
        List<Oggetto> oggetti = oggettiService.findByStoria(storia);
        responseData.put("oggetti", oggetti);

        return responseData;
    }
}

