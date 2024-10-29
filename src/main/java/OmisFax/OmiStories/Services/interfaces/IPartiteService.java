package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.DTOs.PartitaDTO;

import java.util.List;

public interface IPartiteService {
    List<PartitaDTO> trovaPartitePerUtente(String username);
}

