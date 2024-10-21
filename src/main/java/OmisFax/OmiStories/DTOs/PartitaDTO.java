package OmisFax.OmiStories.DTOs;

public class PartitaDTO {
    private StoriaDTO storiaDTO;
    private ScenarioDTO scenarioCorrente;
    private String giocatore; // Nome dell'utente che sta giocando

    public PartitaDTO() {}

    public PartitaDTO(StoriaDTO storiaDTO, ScenarioDTO scenarioCorrente, String giocatore) {
        this.storiaDTO = storiaDTO;
        this.scenarioCorrente = scenarioCorrente;
        this.giocatore = giocatore;
    }

    public StoriaDTO getStoriaDTO() {
        return storiaDTO;
    }

    public void setStoriaDTO(StoriaDTO storiaDTO) {
        this.storiaDTO = storiaDTO;
    }

    public ScenarioDTO getScenarioCorrente() {
        return scenarioCorrente;
    }

    public void setScenarioCorrente(ScenarioDTO scenarioCorrente) {
        this.scenarioCorrente = scenarioCorrente;
    }

    public String getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(String giocatore) {
        this.giocatore = giocatore;
    }
}
