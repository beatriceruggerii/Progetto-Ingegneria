package OmisFax.OmiStories.DTOs;

public class SceltaDTO {
    private long id; //inserimento di id necessario per la modifica

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

    public SceltaDTO(long id, String testo) {
        this.id = id;
        this.testo = testo;
    }

    public SceltaDTO(long id, String testo, long idMadre, long idFiglio) {
        this.id = id;
        this.testo = testo;
        this.idMadre = idMadre;
        this.idFiglio = idFiglio;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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