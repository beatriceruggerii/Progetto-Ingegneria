package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, String > {
    Utente findByUsername(String username);
    void deleteByUsername(String username);


    Utente findByUsernameAndPassword(String username, String password);

}