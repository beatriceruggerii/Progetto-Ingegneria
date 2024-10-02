package OmisFax.OmiStories.DTOs;

public class SceltaDTO {
    private String testo;
    private long idMadre;
    private long idFiglio;

    public SceltaDTO() {
    }

    public SceltaDTO(String testo, long idMadre, long idFiglio) {
        this.testo = testo;
        this.idMadre = idMadre;
        this.idFiglio = idFiglio;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public long getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(long idMadre) {
        this.idMadre = idMadre;
    }

    public long getIdFiglio() {
        return idFiglio;
    }

    public void setIdFiglio(long idFiglio) {
        this.idFiglio = idFiglio;
    }
}
