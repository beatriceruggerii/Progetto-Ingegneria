package OmisFax.OmiStories.Entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Oggetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeOggetto;

    @ManyToOne
    private Scenario scenarioMadre;

    @ManyToOne
    private Scenario scenarioControllore;

    public Oggetto() {
    }

    public Oggetto(String nomeOggetto, Scenario scenarioMadre, Scenario scenarioControllore) {
        this.nomeOggetto = nomeOggetto;
        this.scenarioMadre = scenarioMadre;
        this.scenarioControllore = scenarioControllore;
    }

    public Oggetto(Long id, String nomeOggetto, Scenario scenarioMadre, Scenario scenarioControllore) {
        this.id = id;
        this.nomeOggetto = nomeOggetto;
        this.scenarioMadre = scenarioMadre;
        this.scenarioControllore = scenarioControllore;
    }

    public Scenario getScenarioMadre() {
        return scenarioMadre;
    }

    public void setScenarioMadre(Scenario scenarioMadre) {
        this.scenarioMadre = scenarioMadre;
    }

    public Scenario getScenarioControllore() {
        return scenarioControllore;
    }

    public void setScenarioControllore(Scenario scenarioControllore) {
        this.scenarioControllore = scenarioControllore;
    }

    public String getNomeOggetto() {
        return nomeOggetto;
    }

    public void setNomeOggetto(String nomeOggetto) {
        this.nomeOggetto = nomeOggetto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oggetto oggetto = (Oggetto) o;
        return Objects.equals(id, oggetto.id) && Objects.equals(scenarioMadre, oggetto.scenarioMadre) && Objects.equals(scenarioControllore, oggetto.scenarioControllore) && Objects.equals(nomeOggetto, oggetto.nomeOggetto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scenarioMadre, scenarioControllore, nomeOggetto);
    }

    @Override
    public String toString() {
        return "Oggetto{" +
                "id=" + id +
                ", scenarioMadre=" + scenarioMadre +
                ", scenarioControllore=" + scenarioControllore +
                ", nomeOggetto='" + nomeOggetto + '\'' +
                '}';
    }
}
