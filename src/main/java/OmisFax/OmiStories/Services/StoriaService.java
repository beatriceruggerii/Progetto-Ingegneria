package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoriaService {
    @Autowired
    private StoriaRepository storiaRepository;

    public boolean salvaStoria(Storia storia){
        if (storiaRepository.findStoriaByTitolo(storia.getTitolo()) != null) {
            return false;
        }
        try {
            storiaRepository.save(storia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
