package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndovinelloService {
    @Autowired
    private  IndovinelloRepository indovinelloRepository;

    public boolean registraIndovinello(Indovinello indovinello) {
        try {
            indovinelloRepository.save(indovinello);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
