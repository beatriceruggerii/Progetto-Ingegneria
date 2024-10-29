package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Storia;

import java.util.List;
import java.util.Map;

public interface IStorieService {
    List<Storia> listaStorie();
    Map<String, Object> responseFetchStorie();
    Map<String, Object> responseStorieAutore(String username);
    Map<String, Object> responseFiltroTitolo(String titolo);
}

