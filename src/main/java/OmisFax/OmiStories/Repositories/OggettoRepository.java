package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OggettoRepository extends JpaRepository<Oggetto, Long> {
    Oggetto findOggettoById(Long id);
    Oggetto findOggettoByScenarioControllore(Scenario s);
    Oggetto findOggettoByScenarioMadre(Scenario s);
}
