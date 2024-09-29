//metodo richiamato alla fine di ogni file html per mostrare i dati dell'utente in sessione

fetch('/session_data') // endpoint che ritorna i dati in sessione
    .then(response => response.json())
    .then(data => {
        // debug
        console.log("utente in sessione: " + data.loggedUsername);
        console.log("premium: " + data.isPremium);
        console.log(data);

        // Popola la pagina con i dati ricevuti
        document.getElementById('username').textContent = data.username;
        document.getElementById('isPremium').textContent = data.isPremium ? "Premium" : "Base";
        if (document.getElementById('storyTitle')) {
            document.getElementById('storyTitle').textContent = data.storyTitle || "Titolo non disponibile";
        }    })
    .catch(error => console.error('Errore nel recupero dei dati di sessione:', error));