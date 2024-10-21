CREATE UNIQUE INDEX unico_scenario_iniziale_per_storia
    ON scenario (fk_storia)
    WHERE iniziale = TRUE;
