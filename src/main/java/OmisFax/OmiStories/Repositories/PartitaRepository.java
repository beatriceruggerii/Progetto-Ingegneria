package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {
}
