package OmisFax.OmiStories.Entities;

import jakarta.persistence.*;

@Entity
public class Scelta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String descrizione;

    @ManyToOne
    private Scenario scenarioMadre;

    @ManyToOne
    private Scenario scenarioFiglio;

    public Scelta(long id, String descrizione, Scenario scenarioMadre, Scenario scenarioFiglio) {
        this.id = id;
        this.descrizione = descrizione;
        this.scenarioMadre = scenarioMadre;
        this.scenarioFiglio = scenarioFiglio;
    }
    public Scelta(String descrizione, Scenario scenarioMadre, Scenario scenarioFiglio) {
        this.descrizione = descrizione;
        this.scenarioMadre = scenarioMadre;
        this.scenarioFiglio = scenarioFiglio;
    }

    public Scelta() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Scenario getScenarioMadre() {
        return scenarioMadre;
    }

    public void setScenarioMadre(Scenario scenarioMadre) {
        this.scenarioMadre = scenarioMadre;
    }

    public Scenario getScenarioFiglio() {
        return scenarioFiglio;
    }

    public void setScenarioFiglio(Scenario scenarioFiglio) {
        this.scenarioFiglio = scenarioFiglio;
    }
}
