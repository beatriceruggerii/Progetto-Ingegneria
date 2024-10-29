package OmisFax.OmiStories.Repositories;

import OmisFax.OmiStories.Entities.Inventario;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findAllByPartita(Partita partita);
    Optional<Inventario> findByPartitaAndOggetto(Partita partita, Oggetto oggetto);

    @Modifying
    @Transactional
    @Query("DELETE FROM Inventario i WHERE i.partita.id = :partitaId")
    void deleteByPartitaId(@Param("partitaId") Long partitaId);
}
