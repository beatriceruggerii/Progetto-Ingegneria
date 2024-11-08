document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("nuovaStoria").addEventListener("submit", function (event) {
            event.preventDefault();

            var titolo = document.getElementById('titolo').value;
            var descrizione = document.getElementById('descrizioneIniziale').value;
            const storia = {
                titolo: titolo,
                descrizioneIniziale: descrizione
            }

            fetch('http://localhost:8080/storia/salva', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(storia)
            }).then(response => {
                console.log("richiesta inviata");
                if (response.ok) {
                    window.location.href = './crea_scenario.html';
                } else {
                    return response.text().then(errorMessage => {
                        document.getElementById("errorMessage").textContent = errorMessage;
                        $('#errorModal').modal('show'); // Mostra il modal
                    });
                }
            }).catch(error => {
                console.log('Si è verificato un errore:', error);
            });
        })
    }
);