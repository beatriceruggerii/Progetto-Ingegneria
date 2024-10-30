package OmisFax.OmiStories.DTOs;

//Oggetto DATA TRANSFER OBJECT: formato in cui vengono passati gli oggetti da client a server
public class OggettoDTO {
    private String nomeOggetto;
    private long scenarioMadreOggetto;
    private long scenarioControlloreOggetto;

    public OggettoDTO() {
    }

    public OggettoDTO(String nomeOggetto, long scenarioMadreId, long scenarioControlloreId) {
        this.nomeOggetto = nomeOggetto;
        this.scenarioMadreOggetto = scenarioMadreId;
        this.scenarioControlloreOggetto = scenarioControlloreId;
    }

    public String getNomeOggetto() {
        return nomeOggetto;
    }

    public void setNomeOggetto(String nomeOggetto) {
        this.nomeOggetto = nomeOggetto;
    }

    public long getScenarioMadreOggetto() {
        return scenarioMadreOggetto;
    }

    public void setScenarioMadreOggetto(long scenarioMadreId) {
        this.scenarioMadreOggetto = scenarioMadreId;
    }

    public long getScenarioControlloreOggetto() {
        return scenarioControlloreOggetto;
    }

    public void setScenarioControlloreOggetto(long scenarioControlloreId) {
        this.scenarioControlloreOggetto = scenarioControlloreId;
    }
}