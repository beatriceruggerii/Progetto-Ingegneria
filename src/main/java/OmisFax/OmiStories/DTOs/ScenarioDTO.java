package OmisFax.OmiStories.DTOs;

public class ScenarioDTO {
    private long id; //inserimento di id necessario per la modifica

    private String titolo;

    private String testo;

    public ScenarioDTO() {}

    public ScenarioDTO(String titolo, String testo) {
        this.titolo = titolo;
        this.testo = testo;
    }

    public ScenarioDTO(long id, String testo) {
        this.id = id;
        this.testo = testo;
    }

    public ScenarioDTO(long id, String titolo, String testo) {
        this.id = id;
        this.titolo = titolo;
        this.testo = testo;
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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
}