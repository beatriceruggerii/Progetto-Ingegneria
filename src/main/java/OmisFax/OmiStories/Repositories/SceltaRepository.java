package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SceltaRepository extends JpaRepository<Scelta, Long> {
    List<Scelta> findByScenarioMadre(Scenario s);
    List<Scelta> findByScenarioFiglio(Scenario s);

}
