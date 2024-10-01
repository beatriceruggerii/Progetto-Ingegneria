package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Indovinello> findByStoria(Storia storia){
        return indovinelloRepository.findALlByStoria(storia);
    }



}
