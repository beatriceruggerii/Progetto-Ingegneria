package OmisFax.OmiStories.Entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Oggetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Scenario scenarioMadre;

    @ManyToOne
    private Scenario scenarioControllore;

    private String nomeOggetto;

    public Oggetto() {
    }

    public Oggetto(Scenario scenarioMadre, Scenario scenarioControllore, String nomeOggetto) {
        this.scenarioMadre = scenarioMadre;
        this.scenarioControllore = scenarioControllore;
        this.nomeOggetto = nomeOggetto;
    }

    public Oggetto(Scenario scenarioMadre, Scenario scenarioControllore, String nomeOggetto, Long id) {
        this.scenarioMadre = scenarioMadre;
        this.scenarioControllore = scenarioControllore;
        this.nomeOggetto = nomeOggetto;
        this.id = id;
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
