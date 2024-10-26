package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SceltaRepository extends JpaRepository<Scelta, Long> {
    @Query("SELECT sc FROM Scelta sc " +
            "WHERE sc.scenarioMadre = :scenarioMadre AND TYPE(sc) = Scelta")
    List<Scelta> findByScenarioMadre(@Param("scenarioMadre") Scenario scenarioMadre);

    List<Scelta> findByScenarioFiglio(Scenario s);

    Scelta findById(long id);

    @Query("SELECT sc FROM Scelta sc " +
            "JOIN sc.scenarioMadre sce " +
            "WHERE sce.storia = :storia AND TYPE(sc) = Scelta")
    List<Scelta> findScelteByStoria(@Param("storia") Storia Storia);

}
