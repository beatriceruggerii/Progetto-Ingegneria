package OmisFax.OmiStories.Entities;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Scenario {
    /* classe che può essere usata singolarmente per creare uno scenario finale base
       o estesa con collegamenti o indovinelli tramite scenarioDecorator
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Campo ID unico generato automaticamente
    @ManyToOne //più scenari possono riferirsi ad una storia
    private Storia storia;
    private String testo;
    @OneToOne //un oggetto si riferisce solo ad uno scenario richiedente, uno scenario può richiedere al max un oggetto
    private Oggetto controllo; //oggetto necessario per accedere allo scenario
    private int numeroScenario; //numero di ordine di apparizione dello scenario nella storia

    // Costruttore predefinito richiesto da JPA
    public Scenario() {}
    public Scenario(Storia storia, String testo, int numeroScenario){
        this.storia = storia;
        this.testo = testo;
        this.numeroScenario = numeroScenario;
    }
    public Scenario(Storia storia, String testo, Oggetto controllo, int numeroScenario) {
        this.storia = storia;
        this.testo = testo;
        this.numeroScenario = numeroScenario;
        this.controllo = controllo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Storia getStoria() {
        return storia;
    }

    public void setStoria(Storia storia) {
        this.storia = storia;
    }

    public Oggetto getControllo() {
        return controllo;
    }

    public void setControllo(Oggetto controllo) {
        this.controllo = controllo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumeroScenario() {
        return numeroScenario;
    }

    public void setNumeroScenario(int numeroScenario) {
        this.numeroScenario = numeroScenario;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", storia=" + storia +
                ", testo='" + testo + '\'' +
                ", controllo=" + controllo +
                ", numeroScenario=" + numeroScenario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return id == scenario.id && numeroScenario == scenario.numeroScenario && Objects.equals(storia, scenario.storia) && Objects.equals(testo, scenario.testo) && Objects.equals(controllo, scenario.controllo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storia, testo, controllo, numeroScenario);
    }
}