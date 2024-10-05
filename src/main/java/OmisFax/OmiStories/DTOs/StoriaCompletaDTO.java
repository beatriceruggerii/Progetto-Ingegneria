package OmisFax.OmiStories.DTOs;

public class StoriaCompletaDTO {
    private StoriaDTO storiaDTO;
    private String autore;

    public StoriaCompletaDTO(StoriaDTO storiaDTO, String autore) {
        this.storiaDTO = storiaDTO;
        this.autore = autore;
    }

    public StoriaDTO getStoriaDTO() {
        return storiaDTO;
    }

    public String getAutore() {
        return autore;
    }
}