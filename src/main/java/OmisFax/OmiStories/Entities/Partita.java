package OmisFax.OmiStories.Entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "partita", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"utente_id", "storia_id"})
})
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente giocatore;

    @ManyToOne
    @JoinColumn(name = "storia_id", nullable = false)
    private Storia storia;

    @ManyToOne
    private Scenario ultimoScenario;

    public Partita() {}

    public Partita(Utente giocatore, Storia storia) {
        this.giocatore = giocatore;
        this.storia = storia;
        // inizialmente, ultimo scenario è null
    }

    public Partita(Utente giocatore, Storia storia, Scenario scenario) {
        this.giocatore = giocatore;
        this.storia = storia;
        this.ultimoScenario = scenario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utente getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Utente giocatore) {
        this.giocatore = giocatore;
    }

    public Storia getStoria() {
        return storia;
    }

    public void setStoria(Storia storia) {
        this.storia = storia;
    }

    public Scenario getUltimoScenario() {
        return ultimoScenario;
    }

    public void setUltimoScenario(Scenario ultimoScenario) {
        this.ultimoScenario = ultimoScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partita partita = (Partita) o;
        return Objects.equals(giocatore, partita.giocatore) &&
                Objects.equals(storia, partita.storia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giocatore, storia);
    }
}