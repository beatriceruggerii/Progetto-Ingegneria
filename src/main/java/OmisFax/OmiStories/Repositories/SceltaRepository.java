package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SceltaRepository extends JpaRepository<Scelta, Long> {
    List<Scelta> findByScenarioMadre(Scenario s);
    List<Scelta> findByScenarioFiglio(Scenario s);

    @Query("SELECT sc FROM Scelta sc " +
            "JOIN sc.scenarioMadre sce " +
            "WHERE sce.storia = :storia")
    List<Scelta> findScelteByStoria(@Param("storia") Storia Storia);

}
