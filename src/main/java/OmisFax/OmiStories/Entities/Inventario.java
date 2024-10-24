package OmisFax.OmiStories.Entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Inventario {
    //per ricavare gli oggetti associati ad una singola partita si ricercano tutte le occorrenze dell'id di quella partita nella tabella
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private Partita partita;
    @ManyToOne
    private Oggetto oggetto;
    public Inventario() {}
    public Inventario(Partita partita, Oggetto oggetto) {
        this.partita = partita;
        this.oggetto = oggetto;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Partita getPartita() {
        return partita;
    }
    public void setPartita(Partita partita) {
        this.partita = partita;
    }
    public Oggetto getOggetto() {
        return oggetto;
    }
    public void setOggetto(Oggetto oggetto) {
        this.oggetto = oggetto;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventario that = (Inventario) o;
        return id == that.id && Objects.equals(partita, that.partita) && Objects.equals(oggetto, that.oggetto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partita, oggetto);
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", partita=" + partita +
                ", oggetto=" + oggetto +
                '}';
    }
}