//file richiamato dall'html della partita (gioca.html)
document.addEventListener("DOMContentLoaded", function (event) {
        const urlParams = new URLSearchParams(window.location.search);
        const titoloStoria = decodeURIComponent(urlParams.get('titoloStoria'));
        //durante la partita l'idscenario è mostrato nel path
        const idScenario = decodeURIComponent(urlParams.get('idScenario')); //se lo scenario non è specificato, ritorna la STRINGA 'null'

    if (titoloStoria) {
        document.getElementById("titoloStoria").textContent = titoloStoria;
        if (idScenario !== null && idScenario !== '' && idScenario !== 'null') {
            console.log("idscenario: " + idScenario); //debug
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

function showData(data){
    //recupero dei dati
    let titolo = data.scenario.titolo;
    let testo = data.scenario.testo;
    let id = data.scenario.id;

    document.getElementById("titoloScenario").textContent = titolo;
    document.getElementById("descrizioneScenario").textContent = testo;

    //inserimento delle scelte
    let scelte = fetchScelte(id);



    //load dell'inventario e degli oggetti
}

function fetchScelte(idScenario){
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

        if(scelte == null || scelte.length === 0){
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
                window.location.href = "gioca.html?titoloStoria=" + encodeURIComponent(titoloStoria) + "&idScenario=" + encodeURIComponent(scenarioFiglioId) ;

            });

            listItem.appendChild(sceltaButton);
            scelteContainer.appendChild(listItem);
        });
    })
        .catch(error => console.error("Errore:", error));

}
