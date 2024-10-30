package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StoriaService implements IStoriaService {
    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private StoriaFactory storiaFactory;


    @Override
    public Storia salvaStoria(StoriaDTO payload, String username) {
        return storiaFactory.createStoriaCompleta(payload,username);
    }

    public Storia getStoria(String titolo) {
        return storiaRepository.findStoriaByTitolo(titolo);
    }

    public Storia findStoriaByTitolo(String titolo) {
        return storiaRepository.findStoriaByTitolo(titolo);
    }


}
