DROP INDEX IF EXISTS unico_scenario_iniziale_per_storia;

CREATE UNIQUE INDEX unico_scenario_iniziale_per_storia
    ON scenario (fk_storia)
    WHERE iniziale = TRUE;

ALTER TABLE inventario DROP CONSTRAINT IF EXISTS fk_partita;

-- Crea un nuovo vincolo di chiave esterna con ON DELETE CASCADE tra partita e inventario
ALTER TABLE inventario
    ADD CONSTRAINT fk_partita FOREIGN KEY (partita_id) REFERENCES partita(id) ON DELETE CASCADE;
