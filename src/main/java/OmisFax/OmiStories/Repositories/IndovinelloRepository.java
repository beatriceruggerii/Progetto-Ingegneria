package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndovinelloRepository extends JpaRepository<Indovinello, Long> {
    Indovinello findById(long id);

    @Query("SELECT i FROM Indovinello i WHERE  i.scenarioMadre.storia = :storia AND i.rispostaCorretta IS NOT NULL")
    List<Indovinello> findByStoria(@Param("storia") Storia storia);

    @Query("SELECT i FROM Indovinello i WHERE  i.scenarioMadre= :scenario AND i.rispostaCorretta IS NOT NULL")
    List<Indovinello> findByScenarioMadre(@Param("scenario") Scenario scenario);
}
