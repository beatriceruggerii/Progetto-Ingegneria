package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.OggettoDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.InventarioRepository;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class OggettoService {
    @Autowired
    private OggettoRepository oggettoRepository;

    @Autowired
    private OggettoFactory oggettoFactory;




    public String salvaOggetto(OggettoDTO payload, Storia storia) {
        String nomeOggetto = payload.getNomeOggetto();
        long idMadre = payload.getScenarioMadreOggetto();
        long idControllore = payload.getScenarioControlloreOggetto();
        Oggetto oggetto = oggettoFactory.createOggetto(nomeOggetto, idMadre, idControllore, storia);
        oggettoRepository.save(oggetto);
        return "Oggetto salvato con successo.";
    }


    public Optional<Oggetto> findById(Long idOggetto) {
        // Recupera l'oggetto dal database
        return oggettoRepository.findById(idOggetto);
    }
}