fetch('http://localhost:8080/fetch_storie_utente')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch storie dell'utente inviata");
        if (data && data.listaStorieUtenteDTOs) {
            let storie = data.listaStorieUtenteDTOs;

            mostraElencoStorie(storie); //metodo
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

function mostraElencoStorie(storieCompletaDTOS){
    if (!Array.isArray(storieCompletaDTOS)) {
        console.error("storieCompletaDTOS non è un array valido:", storieCompletaDTOS);
        return;
    }

    //inserimento delle storie nel catalogo (catalogo.html)
    let elenco = document.getElementById("elenco");

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
            "<input type=\"button\" onclick=\"redirectModificaStoria('" + encodeURIComponent(storiaDTO.titolo) + "')\" class=\"btn btn-custom bs-tooltip-end\" value=\"Modifica\">" +
            "</div>"+
            "</li>";
    });
    elenco.innerHTML = html;
}


function redirectModificaStoria(titolo){
    // Passa il titolo come parametro nella URL della nuova pagina
    window.location.href = "modifica-storia.html?titoloStoria=" + titolo;

}
