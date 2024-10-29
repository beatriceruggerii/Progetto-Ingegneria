package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.DTOs.OggettoDTO;

import java.util.Optional;

public interface IOggettoService {
    String salvaOggetto(OggettoDTO payload, Storia storia);
    Optional<Oggetto> findById(Long idOggetto);
}

