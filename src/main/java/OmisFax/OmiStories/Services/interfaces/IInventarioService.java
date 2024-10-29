package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Entities.Partita;

import java.util.List;

public interface IInventarioService {
    Inventario aggiungiOggettoAInventario(Long idPartita, Long idOggetto);
    List<Inventario> getInventarioPartita(long idPartita);
    public List<Inventario> findAllByPartita(Partita partita);
    public void deleteById(long idPartita);
}

