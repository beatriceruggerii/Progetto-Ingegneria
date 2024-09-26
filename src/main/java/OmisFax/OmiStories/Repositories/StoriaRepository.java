package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoriaRepository extends JpaRepository<Storia, String> {
    Storia findStoriaByTitolo(String titolo);
    List<Storia> findByAutore(Utente u);

}
