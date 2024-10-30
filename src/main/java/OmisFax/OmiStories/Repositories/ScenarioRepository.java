package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario,Long> {
    List<Scenario> findByStoriaOrderById(Storia storia);
    List<Scenario> findByStoria(Storia storia);
    Scenario findByTitoloAndStoria(String titolo, Storia storia);
    Scenario findByStoriaAndInizialeTrue(Storia storia);

}
