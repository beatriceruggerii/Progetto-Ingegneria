package OmisFax.OmiStories.DTOs;

public class IndovinelloDTO {

    private String testo;
    private String soluzione;
    private long idMadre;
    private long idFiglio;

    public IndovinelloDTO() {
    }

    public IndovinelloDTO(String testo, String soluzione, long idMadre, long idFiglio) {
        this.testo = testo;
        this.soluzione = soluzione;
        this.idMadre = idMadre;
        this.idFiglio = idFiglio;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getSoluzione() {
        return soluzione;
    }

    public void setSoluzione(String soluzione) {
        this.soluzione = soluzione;
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
