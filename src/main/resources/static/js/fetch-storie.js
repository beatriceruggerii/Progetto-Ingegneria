fetch('http://localhost:8080/fetch_storie')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    }).then(data => {
    console.log("richiesta fetch storie inviata");
    console.log("Dati ricevuti:", data); // Aggiungi questo per verificare i dati ricevuti
    if (Array.isArray(data) && data.length > 0) {
        window.storiaCompletaDTOS = data;
        mostraStorie(window.storiaCompletaDTOS);
    } else if (typeof data === 'object' && data.storiaCompletaDTOS) {
        // Se data contiene una lista sotto il campo storiaCompletaDTOS
        window.storiaCompletaDTOS = data.storiaCompletaDTOS;
        mostraStorie(window.storiaCompletaDTOS);
    } else {
        console.log("Dati non validi ricevuti:", data);
    }
})
    .catch(error => {
        console.error('Errore:', error);
    });


function mostraStorie(storieCompletaDTOS){

    if (!Array.isArray(storieCompletaDTOS)) {
        console.error("storieCompletaDTOS non è un array valido:", storieCompletaDTOS);
        return;
    }

    //inserimento delle storie nel catalogo (catalogo.html)
    let catalogo = document.getElementById("catalogo");

    let html = '';
    storieCompletaDTOS.forEach(storiaCompletaDTO => {
        let storiaDTO = storiaCompletaDTO.storiaDTO;  // Accedi all'oggetto StoriaDTO
        let autore = storiaCompletaDTO.autore;  // Accedi all'autore
        console.log("Autore: ", autore);  // Verifica se autore è un oggetto con username
        html +=
            "<li class=\"list-group-item d-flex justify-content-between align-items-center\">\n" +
            "<div class=\"col-md-8\">" +
            "<h3 class=\"fw-bold text-start\">Titolo: " + storiaDTO.titolo + "</h3>\n" +
            "<p class=\"text-muted text-start\">Descrizione: "+ storiaDTO.descrizioneIniziale +"</p>"+
            "<p class=\"text-muted text-start\">Autore:"+ autore +"</p>"+
            "</div>"+
            "<div class=\"col-md-4 text-end\">"+
            "<input type=\"submit\" class=\"btn btn-custom bs-tooltip-end\" value=\"Gioca\">"+
            "</div>"+
            "</li>";
    });
    catalogo.innerHTML = html;
}