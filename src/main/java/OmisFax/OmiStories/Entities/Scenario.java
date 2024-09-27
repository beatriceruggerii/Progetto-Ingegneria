package OmisFax.OmiStories.Entities;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_storia")
    private Storia storia;
    private String titolo; //numero di ordine di apparizione dello scenario nella storia
    private String testo;


    // Costruttore predefinito richiesto da JPA
    public Scenario() {}
    public Scenario(Storia storia, String titolo, String testo){
        this.storia = storia;
        this.titolo = titolo;
        this.testo = testo;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", storia=" + storia +
                ", titolo='" + titolo + '\'' +
                ", testo='" + testo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return id == scenario.id && Objects.equals(storia, scenario.storia) && Objects.equals(titolo, scenario.titolo) && Objects.equals(testo, scenario.testo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storia, titolo, testo);
    }
}