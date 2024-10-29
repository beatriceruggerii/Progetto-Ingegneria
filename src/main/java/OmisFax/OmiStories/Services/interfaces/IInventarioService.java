package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Inventario;

import java.util.List;

public interface IInventarioService {
    Inventario aggiungiOggettoAInventario(Long idPartita, Long idOggetto);
    List<Inventario> getInventarioPartita(long idPartita);
}

