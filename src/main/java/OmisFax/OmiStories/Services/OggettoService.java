package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
