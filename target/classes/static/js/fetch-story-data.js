//file richiamato dall'html della partita (gioca.html)
document.addEventListener("DOMContentLoaded", function (event) {
        const urlParams = new URLSearchParams(window.location.search);
        const titoloStoria = decodeURIComponent(urlParams.get('titoloStoria'));
        //durante la partita l'idscenario è mostrato nel path
        const idScenario = decodeURIComponent(urlParams.get('idScenario')); //se lo scenario non è specificato, ritorna la STRINGA 'null'

        if (titoloStoria) {
            document.getElementById("titoloStoria").textContent = titoloStoria;
            if (idScenario !== null && idScenario !== '' && idScenario !== 'null') {
                // è stato specificato uno scenario a cui accedere
                console.log("idscenario: " + idScenario); //debug
                fetchScenario(idScenario);

            } else { //se non c'è l'id allora la partita è in fase iniziale
                console.log("Partita da inziare"); //debug
                fetch(`fetch_scenario_iniziale/${titoloStoria}`)
                    .then(response => {
                        if (!response.ok) {
                            return response.text().then(errorMessage => {
                                document.getElementById("errorMessage").textContent = errorMessage;
                                $('#errorModal').modal('show'); // Mostra il modal
                            });
                        }
                        //debug
                        console.log("scenario ricevuto");
                        return response.json();
                    }).then(data => {
                    console.log(data);
                    showData(data);
                })
                    .catch(error => console.error("Errore:", error));
            }
        }
    }
)

function fetchScenario(idScenario){
    fetch(`scenario/${idScenario}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
            return response.json();
        }).then(data => {
        //debug
        console.log("scenario ricevuto");
        console.log(data);
        showData(data);

    })
        .catch(error => console.error("Errore:", error));
}

//funzione che controlla l'esisitenza di oggetti richiesti per accedere allo scenario
function checkOggetti(idScenario, titoloStoria) {
    fetch(`oggetti/controllori/${idScenario}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
            return response.json();
        }).then(data => {
        console.log("Oggetti controllori: ");
        console.log(data);

        //controllo se lo scenario va bloccato
        if (Array.isArray(data.oggettiMancanti) && data.oggettiMancanti.length > 0){
            let oggettiNecessari = "";
            data.oggettiMancanti.forEach(oggetto =>{
                oggettiNecessari = oggettiNecessari + oggetto.nomeOggetto + " ";
            })
            document.getElementById("modal-title").textContent = "Non puoi accedere a questo scenario";
            document.getElementById("successMessage").textContent = "Non hai gli oggetti necessari: " + oggettiNecessari;
            $('#successModal').modal('show'); // Mostra il modal
        } else{
            // è possibile accedere qallo scenario
            window.location.href = "gioca.html?titoloStoria=" + encodeURIComponent(titoloStoria) + "&idScenario=" + encodeURIComponent(idScenario);

        }
    });
}

function showData(data) {
    //recupero dei dati
    let titolo = data.scenario.titolo;
    let testo = data.scenario.testo;
    let id = data.scenario.id;

    document.getElementById("titoloScenario").textContent = titolo;
    document.getElementById("descrizioneScenario").textContent = testo;

    //inserimento delle scelte
    let scelte = fetchScelte(id);


    //load dell'inventario e degli oggetti
    fetchOggettiRaccoglibili(id);
}

function fetchScelte(idScenario) {
    fetch(`fetch_scelte/${idScenario}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
            return response.json();
        }).then(data => {
        //debug
        console.log("dati ricevuti da fetch scelte")
        console.log(data);
        const scelte = data.scelte;

        if (scelte == null || scelte.length === 0) {
            document.getElementById("modal-title").textContent = "Partita terminata.";
            document.getElementById("successMessage").textContent = "Complimenti! Hai concluso la storia.";
            $('#successModal').modal('show'); // Mostra il modal
        }

        const scelteContainer = document.getElementById("scelteContainer");
        if (!scelteContainer) return;

        // Aggiungo le scelte al documento
        scelte.forEach(scelta => {
            const listItem = document.createElement("li");
            listItem.classList.add("list-group-item");

            const sceltaButton = document.createElement("button");
            sceltaButton.textContent = scelta.descrizione;
            sceltaButton.classList.add('btn', 'btn-link');

            // Aggiungo un listener per il click
            sceltaButton.addEventListener("click", () => {
                const scenarioFiglioId = scelta.scenarioFiglio.id;
                const titoloStoria = scelta.scenarioFiglio.storia.titolo;
                aggiornaPartita(scenarioFiglioId);
                checkOggetti(scenarioFiglioId, titoloStoria);
            });

            listItem.appendChild(sceltaButton);
            scelteContainer.appendChild(listItem);
        });
    })
        .catch(error => console.error("Errore:", error));

}


function fetchOggettiRaccoglibili(idScenario) {
    fetch(`oggetti/${idScenario}`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
            return response.json();
        }).then(data => {
        console.log("Oggetti trovati: ");
        console.log(data);

        const oggetti = data.oggetti;

        const oggettiContainer = document.getElementById("oggettiDisponibili");

        oggetti.forEach(oggetto => {
            const listItem = document.createElement("li");
            listItem.classList.add("list-group-item");

            const oggettoButton = document.createElement("button");
            const oggettoLabel = document.createElement("label");
            oggettoLabel.textContent = oggetto.nomeOggetto;
            oggettoButton.value = "Raccogli";
            oggettoButton.textContent = "Raccogli"
            oggettoButton.classList.add('btn', 'btn-link');

            listItem.id = "oggetto" + oggetto.id;

            // Aggiungo un listener per il click
            oggettoButton.addEventListener("click", () => {
                raccogliOggetto(oggetto)
            });

            listItem.appendChild(oggettoLabel);
            listItem.appendChild(oggettoButton);
            oggettiContainer.appendChild(listItem);
        });

    })
        .catch(error => console.error("Errore:", error));

}

function fetchInventario() {
    fetch(`inventario/`)
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
            return response.json();
        }).then(data => {
        console.log("Oggetti trovati: ");
        console.log(data);

        const inventario = data.inventario;

        const inventarioContainer = document.getElementById("inventario");

        inventario.forEach(elemento => {
            const listItem = document.createElement("li");
            listItem.classList.add("list-group-item");

            const oggettoLabel = document.createElement("p");
            oggettoLabel.textContent = elemento.oggetto.nomeOggetto;

            listItem.appendChild(oggettoLabel);
            inventarioContainer.appendChild(listItem);
        });

    })
        .catch(error => console.error("Errore:", error));

}


function raccogliOggetto(oggetto) {
    fetch('http://localhost:8080/inventario/aggiungi', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({idOggetto: oggetto.id})
    }).then(response => {
        if (response.ok) {
            console.log("Oggetto aggiunto correttamente!");
            //rimuovo l'elemento raccolto dagli oggetti disponibli
            const elemento = document.getElementById("oggetto" + oggetto.id);
            if (elemento) {
                console.log("Elemento trovato: ", elemento);
                elemento.remove();
            } else {
                console.log("Elemento con ID: '" + "oggetto" + oggetto.id + "' non trovato");
            }
            fetchInventario();

        } else {
            console.error("Errore durante l'aggiunta dell'oggetto");
        }
    })
        .catch(error => {
            console.error("Errore di rete: ", error);
        });
}

