package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndovinelloRepository extends JpaRepository<Indovinello, Long> {
    Indovinello findById(long id);
}
