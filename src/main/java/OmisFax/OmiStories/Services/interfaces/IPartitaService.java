package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Partita;

import java.util.Optional;

public interface IPartitaService {
    Partita salvaPartita(String titoloStoria, String username);
    boolean aggiornaPartita(long idPartita, long idScenarioFiglio);
    void deleteById(long idPartita);
    Partita getPartita(long idPartita);
    Optional<Partita> findById(Long idPartita);
}

