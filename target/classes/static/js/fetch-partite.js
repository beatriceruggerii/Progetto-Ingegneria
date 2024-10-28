fetch('http://localhost:8080/partite/')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch partite inviata");
        console.log("Dati ricevuti:", data);

        if (Array.isArray(data) && data.length > 0) {
            partiteDTOs = data;
            mostraPartite(partiteDTOs);
        } else if (typeof data === 'object' && data.partitaDTOs) {
            partite = data.partitaDTOs;
            mostraPartite(partite);
        } else {
            console.log("Dati non validi ricevuti:", data);
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

function mostraPartite(partiteDTOs) {
    if (!Array.isArray(partiteDTOs)) {
        console.error("partiteDTOs non è un array valido:", partiteDTOs);
        return;
    }

    let catalogo = document.getElementById("partiteIniziate");
    let html = '';

    partiteDTOs.forEach(partitaDTO => {
        let storiaDTO = partitaDTO.storiaDTO;
        let scenarioCorrente = partitaDTO.scenarioCorrente;

        let titolo = storiaDTO.titolo;
        let autore = partitaDTO.giocatore;

        html +=
            `<li class="list-group-item d-flex justify-content-between align-items-center">
                <div class="col-md-8">
                    <h3 class="fw-bold text-start">Titolo: ${titolo}</h3>
                    <p class="text-muted text-start">Descrizione: ${storiaDTO.descrizioneIniziale}</p>
                    <p class="text-muted text-start">Scenario attuale: ${scenarioCorrente.titolo}</p>
                    <p class="text-muted text-start">Autore: ${autore}</p>
                </div>
                <div class="col-md-4 text-end">
                    <button onclick="riprendiPartita('${titolo}', ${partitaDTO.idPartita} , '${scenarioCorrente.id}')" class="btn btn-custom">Continua</button>
                    <button onclick="eliminaPartita(${partitaDTO.idPartita})" class="btn m-3 btn-custom">Elimina partita</button>
                </div>
            </li>`;
    });

    catalogo.innerHTML = html;
}

function riprendiPartita(titoloStoria, idPartita, idScenario) {
    fetch(`http://localhost:8080/partita/riprendi/${idPartita}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show');
                });
            } else {
                window.location.href = "gioca.html?titoloStoria=" + encodeURIComponent(titoloStoria) + "&idScenario=" + encodeURIComponent(idScenario);
            }
        })
        .catch(error => console.error('Errore:', error));
}

function eliminaPartita(idPartita) {
    if (confirm("Sei sicuro di voler eliminare questa partita?")) {
        fetch(`http://localhost:8080/partite/${idPartita}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore durante l\'eliminazione della partita');
                }
                console.log("Partita eliminata con successo:", idPartita);
                return fetch('http://localhost:8080/partite/');
            })
            .then(response => response.json())
            .then(data => {
                mostraPartite(data);
            })
            .catch(error => {
                console.error("Errore durante l'eliminazione:", error);
            });
    }
}
