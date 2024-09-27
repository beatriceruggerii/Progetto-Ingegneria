package OmisFax.OmiStories.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

/*tipo di scelta che prevede la presenza di una risposta corretta
    la scelta (e quindi lo scenario che segue) è accessibile solo se la risposta inserita dall'utente è corretta*/
@Entity
@Table(name = "indovinello")
public class Indovinello extends Scelta {
    //ID: id della scelta che estende
    private String rispostaCorretta;
    public Indovinello() {}
    public Indovinello(long id, String descrizione, Scenario scenarioMadre, Scenario scenarioFiglio, String rispostaCorretta) {
        super(id, descrizione, scenarioMadre, scenarioFiglio);
        this.rispostaCorretta = rispostaCorretta;
    }

    public Indovinello(String descrizione, Scenario scenarioMadre, Scenario scenarioFiglio, String rispostaCorretta) {
        super(descrizione, scenarioMadre, scenarioFiglio);
        this.rispostaCorretta = rispostaCorretta;
    }

    public Indovinello(String rispostaCorretta) {
        this.rispostaCorretta = rispostaCorretta;
    }

    public String getRispostaCorretta() {
        return rispostaCorretta;
    }
    public void setRispostaCorretta(String rispostaCorretta) {
        this.rispostaCorretta = rispostaCorretta;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indovinello that = (Indovinello) o;
        return Objects.equals(rispostaCorretta, that.rispostaCorretta);
    }
    @Override
    public int hashCode() {
        return Objects.hash(rispostaCorretta);
    }
    @Override
    public String toString() {
        return "Indovinello{" +
                "rispostaCorretta='" + rispostaCorretta + '\'' +
                '}';
    }
}
