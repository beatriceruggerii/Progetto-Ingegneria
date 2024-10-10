package OmisFax.OmiStories.DTOs;

public class IndovinelloDTO {
    private long id; //inserimento di id necessario per la modifica

    private String testo;
    private String soluzione;
    private long idMadre;
    private long idFiglio;

    public IndovinelloDTO() {
    }

    public IndovinelloDTO(long id, String testo, String soluzione, long idMadre, long idFiglio) {
        this.id = id;
        this.testo = testo;
        this.soluzione = soluzione;
        this.idMadre = idMadre;
        this.idFiglio = idFiglio;
    }

    public IndovinelloDTO(long id, String testo, String soluzione) {
        this.id = id;
        this.testo = testo;
        this.soluzione = soluzione;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IndovinelloDTO(String testo, String soluzione, long idMadre, long idFiglio) {
        this.testo = testo;
        this.soluzione = soluzione;
        this.idMadre = idMadre;
        this.idFiglio = idFiglio;
    }

    public IndovinelloDTO(String testo, String soluzione){
        this.testo = testo;
        this.soluzione = soluzione;
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

