package OmisFax.OmiStories.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;


@Entity
public class Storia {
    //ID: titolo storia, non possono esistere due storie con lo stesso titolo
    @Id
    private String titolo;
    @ManyToOne
    private Utente autore; //utente.getusernane

    public Storia(String titolo, Utente autore) {
        this.titolo = titolo;
        this.autore = autore;
    }

    public Storia() {
    }

    public String getTitolo() {
        return titolo;
    }
    public String getId() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Utente getAutore() {
        return autore;
    }

    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Storia storia = (Storia) o;
        return Objects.equals(titolo, storia.titolo) && Objects.equals(autore, storia.autore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, autore);
    }

    @Override
    public String toString() {
        return "Storia{" +
                "titolo='" + titolo + '\'' +
                ", autore=" + autore +
                '}';
    }
}
