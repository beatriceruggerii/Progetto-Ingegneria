package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findAllByPartita(Partita partita);
}
