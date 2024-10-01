package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OggettoService {
    @Autowired
    private OggettoRepository oggettoRepository;

    public boolean salvaOggetto(Oggetto oggetto){
        try {
            oggettoRepository.save(oggetto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Oggetto> findByStoria(Storia storia){
        return oggettoRepository.findALlByStoria(storia);
    }
}
