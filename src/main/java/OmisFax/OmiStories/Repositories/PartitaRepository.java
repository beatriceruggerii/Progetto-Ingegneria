package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import java.util.List;


@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {
    List<Partita> findByGiocatoreAndStoria(Utente giocatore, Storia storia);
    List<Partita> findByGiocatoreUsername(String username);

    Partita findById(long id);

}
