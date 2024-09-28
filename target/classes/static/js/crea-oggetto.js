document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("nuovoOggetto").addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(event.target);

        // converto FormData in un oggetto scenario che contiene tutti i valori del form
        const oggetto = {};
        formData.forEach((value, key) => {
            oggetto[key] = value;
        });

        fetch('http://localhost:8080/salva_oggetto', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(oggetto)
        }).then(response => {
            console.log("richiesta inviata");
            if (response.ok) {
                window.location.href = './crea_scenario.html';
            } else {
                throw new Error('Errore nella richiesta');
            }
        })
            .catch(error => {
                console.log('Si è verificato un errore:', error);
            });
    });
});