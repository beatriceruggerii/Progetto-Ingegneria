package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaCompletaDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import OmisFax.OmiStories.Services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
