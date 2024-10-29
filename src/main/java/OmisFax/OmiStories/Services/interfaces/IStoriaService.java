package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.DTOs.StoriaDTO;

import java.util.List;
import java.util.Map;

public interface IStoriaService {
    Storia salvaStoria(StoriaDTO payload, String username);
    Storia getStoria(String titolo);
    Storia findStoriaByTitolo(String titolo);
}

