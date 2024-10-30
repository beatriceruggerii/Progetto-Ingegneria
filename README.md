# Progetto-Ingegneria

Progetto per il corso di Ingegneria del Software, Informatica per il Management anno 2023/2024

Il progetto consiste nello sviluppo di un'applicazione web in cui gli utenti possono creare e giocare storie interattive. Le funzionalità includono registrazione, creazione, modifica, salvataggio e ripresa di storie, gestione dell'inventario e pagamenti per accedere a specifiche modalità di gioco.


Realizzato dagli studenti:
* Fratoni Leonardo
* Mazzocchi Thomas
* Ruggeri Beatrice
* Zoccolillo Matilde

## Specifiche tecniche del progetto

### Informazioni sullo stack
Lo stack tecnologico include:

* Frontend:
  - Html, Css, JavaScript
* Backend:
  -	Spring Boot: viene utilizzato il framework Spring per la realizzazione del backend.
  - Database: viene utilizzato PostgreSQL per la memorizzazione e la gestione dei dati
  - La comunicazione client-server avviene traminte richieste HTTP, gli endpoint sono strutturati secondo lo standard OpenApi 3.0.
* Infrastruttura e Deployment:
  - GitHub: per il controllo di versione del codice sorgente, la collaborazione e la gestione dei rami di sviluppo
  - Docker: viene adottato Docker per il deployment dell'applicazione, garantendo un ambiente consistente e isolato per l'esecuzione dei diversi servizi


### Deploy
Unico requisito per il deploy dell'applicativo è Docker.

Scaricata la repository, eseguire all'interno della cartella il comando

```bash
docker-compose up --build
```

Una volta avviati i diversi servizi, sarà possibile accedere alla piattaforma tramite il link

http://localhost:8080/

Per terminare l'esecuzione, eseguire all'interno della cartella il comando

```bash
docker-compose down
```

