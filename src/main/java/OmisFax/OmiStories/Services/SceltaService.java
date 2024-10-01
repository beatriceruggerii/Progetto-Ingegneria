package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceltaService {
    @Autowired
    private SceltaRepository sceltaRepository;

    public boolean registraScelta(Scelta scelta) {
        try {
            System.out.println("fin qui tutto bene");
            sceltaRepository.save(scelta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Scelta> listaScelte(Storia storia){
        return sceltaRepository.findScelteByStoria(storia);
    }
}
