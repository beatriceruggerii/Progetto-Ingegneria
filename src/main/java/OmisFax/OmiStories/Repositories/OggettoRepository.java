package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OggettoRepository extends JpaRepository<Oggetto, Long> {
    Oggetto findOggettoById(Long id);
    List<Oggetto> findByScenarioControllore(Scenario s);
    List<Oggetto> findByScenarioMadre(Scenario s);
    @Query("SELECT o FROM Oggetto o WHERE o.scenarioMadre.storia = :storia")
    List<Oggetto> findAllByStoria(@Param("storia") Storia storia);
}
