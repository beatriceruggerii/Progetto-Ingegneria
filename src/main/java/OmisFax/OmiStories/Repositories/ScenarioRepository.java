package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario,Long> {
    List<Scenario> findByStoriaOrderById(Storia storia);
    Scenario findById(long id);
    List<Scenario> findByStoria(Storia storia);
    Scenario findByTitoloAndStoria(String titolo, Storia storia);

}
