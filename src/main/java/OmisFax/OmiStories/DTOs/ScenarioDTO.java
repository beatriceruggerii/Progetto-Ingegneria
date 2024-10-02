package OmisFax.OmiStories.DTOs;

public class ScenarioDTO {

    private String titolo;

    private String testo;

    public ScenarioDTO() {}

    public ScenarioDTO(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}
