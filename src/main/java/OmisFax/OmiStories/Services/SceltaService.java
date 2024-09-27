package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaService {
    @Autowired
    private SceltaRepository sceltaRepository;

    public boolean registraScelta(Scelta scelta) {
        try {
            sceltaRepository.save(scelta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
