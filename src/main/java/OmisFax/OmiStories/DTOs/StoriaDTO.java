package OmisFax.OmiStories.DTOs;

public class StoriaDTO {

    private String titolo;
    private String descrizioneIniziale;

    public StoriaDTO() {
    }

    public StoriaDTO(String titolo, String descrizioneIniziale) {
        this.titolo = titolo;
        this.descrizioneIniziale = descrizioneIniziale;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizioneIniziale() {
        return descrizioneIniziale;
    }

    public void setDescrizioneIniziale(String descrizioneIniziale) {
        this.descrizioneIniziale = descrizioneIniziale;
    }
}
