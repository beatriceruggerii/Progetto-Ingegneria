function aggiornaPartita(scenarioFiglioId) {
    fetch('http://localhost:8080/partita/aggiorna', {
    method: 'POST',
        headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(scenarioFiglioId)
})
        .then(response => {
            if (response.ok) {
                console.log('Partita Aggiornata');
            } else {
                console.error('Errore durante il logout:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Errore durante il logout:', error);
        });
}