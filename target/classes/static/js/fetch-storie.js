//script eseguito da catalogo.html

fetch('http://localhost:8080/storie/')
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
            "<button onclick=\"redirectGiocaStoria('" + encodeURIComponent(storiaDTO.titolo) + "')\" class=\"btn btn-custom bs-tooltip-end\">Gioca</button>" +
            "</form>"+
            "</div>"+
            "</li>";
    });
    catalogo.innerHTML = html;
}

async function redirectGiocaStoria(titolo) {
    // Controllo che l'utente sia premium
    fetch('/session_data') // endpoint che ritorna i dati in sessione
        .then(response => response.json())
        .then(async data => {
            console.log(data);
            if (!data.isPremium) {
                // Mostra modal che blocca
                console.log("l'utente non è premimum");
                document.getElementById("errorMessage").textContent = "Passa a premium per poter giocare!";
                $('#errorModal').modal('show'); // Mostra il modal
            } else {
                console.log("l'utente è premimum");
                // Chiama salvaPartita e aspetta la sua conclusione
                const success = await salvaPartita(decodeURIComponent(titolo));

                if (success) {
                    // Reindirizza solo se il salvataggio è andato a buon fine
                    window.location.href = "gioca.html?titoloStoria=" + titolo;
                }
            }
        })
}

async function salvaPartita(titoloStoria) {
    console.log("Salvataggio partita"); // Debug
    console.log(titoloStoria); // Debug

    try {
        const response = await fetch('http://localhost:8080/partita/salva', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain',
            },
            body: titoloStoria
        });

        if (!response.ok) {
            if (response.status === 401) {
                alert("Hai già iniziato questa partita, verrai reindirizzato alle tue partite");
                window.location.href = "le-mie-partite.html";
                return false;
            }
            throw new Error('Errore durante il salvataggio della partita');
        }

        console.log("Partita salvata con successo");
        return true;

    } catch (error) {
        console.error('Errore:', error);
        return false;
    }
}
