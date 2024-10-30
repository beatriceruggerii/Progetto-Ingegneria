package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {
    List<Partita> findByGiocatoreAndStoria(Utente giocatore, Storia storia);
    List<Partita> findByGiocatoreUsername(String username);
    @Query("SELECT p FROM Partita p WHERE p.giocatore.username = :username AND p.storia.titolo = :titoloStoria")
    Partita findByGiocatoreUsernameAndStoriaTitolo(@Param("username") String username, @Param("titoloStoria") String titoloStoria);
    void deleteById(long id);
    Partita findById(long id);

}
