fetch('http://localhost:8080/partite/')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    }).then(data => {
    console.log("richiesta fetch partite inviata");
    console.log("Dati ricevuti:", data); // Aggiungi questo per verificare i dati ricevuti
    if (Array.isArray(data) && data.length > 0) {
        partiteDTOs = data;
        mostraPartite(partiteDTOs);
    } else if (typeof data === 'object' && data.partitaDTOs) {
        // Se data contiene una lista sotto il campo storiaCompletaDTOS
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

    // Inserimento delle partite nel catalogo
    let catalogo = document.getElementById("partiteIniziate");
    let html = '';

    partiteDTOs.forEach(partitaDTO => {
        let storiaDTO = partitaDTO.storiaDTO;  // Accedi all'oggetto StoriaDTO
        let scenarioCorrente = partitaDTO.scenarioCorrente;  // Accedi allo scenario corrente

        let titolo = storiaDTO.titolo;
        let autore = partitaDTO.giocatore;

        html +=
            "<li class=\"list-group-item d-flex justify-content-between align-items-center\">" +
            "<div class=\"col-md-8\">" +
            "<h3 class=\"fw-bold text-start\">Titolo: " + titolo + "</h3>" +
            "<p class=\"text-muted text-start\">Descrizione: " + storiaDTO.descrizioneIniziale + "</p>" +
            "<p class=\"text-muted text-start\">Scenario attuale: " + scenarioCorrente.titolo + "</p>" +
            "<p class=\"text-muted text-start\">Autore: " + autore + "</p>" +
            "</div>" +
            "<div class=\"col-md-4 text-end\">" +
            "<button onclick='redirectGiocaStoria(\"" + encodeURIComponent(titolo) + "\")' class=\"btn btn-custom\">Continua</button>" +
            "</div>" +
            "</li>";

    });

    catalogo.innerHTML = html;
}

function redirectGiocaStoria(titolo) {
    // Passa il titolo come parametro nella URL della nuova pagina
    salvaPartita(titolo);
    window.location.href = "gioca.html?titoloStoria=" + titolo;

}