package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Repositories.InventarioRepository;
import OmisFax.OmiStories.Services.interfaces.IInventarioService;
import OmisFax.OmiStories.Services.interfaces.IOggettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService implements IInventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private IOggettoService oggettoService;

    @Autowired
    private PartitaService partitaService;


    public Inventario aggiungiOggettoAInventario(Long idPartita, Long idOggetto) {
        // Recupera la partita dal database
        Partita partita = partitaService.findById(idPartita).get();

        Oggetto oggetto = oggettoService.findById(idOggetto).get();

        Optional<Inventario> inventarioOptional = inventarioRepository.findByPartitaAndOggetto(partita, oggetto);

        if(inventarioOptional.isPresent()){
            throw new IllegalArgumentException("Hai già raccolto questo oggetto");
        }

        // Crea una nuova istanza di Inventario
        Inventario inventario = new Inventario(partita, oggetto);

        // Salva l'istanza di Inventario nel database
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> getInventarioPartita(long idPartita){
        Partita partita = partitaService.findById(idPartita).get();
        return inventarioRepository.findAllByPartita(partita);
    }

    public List<Inventario> findAllByPartita(Partita partita){
        return inventarioRepository.findAllByPartita(partita);
    }

    public void deleteByPartitaId(long idPartita){
        inventarioRepository.deleteByPartitaId(idPartita);
    }
}

