package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.DTOs.StoriaDTO;

public interface IStoriaService {
    Storia salvaStoria(StoriaDTO payload, String username);
    Storia getStoria(String titolo);
    Storia findStoriaByTitolo(String titolo);
}

