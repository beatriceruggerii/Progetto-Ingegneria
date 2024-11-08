document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("nuovoScenario").addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(event.target);

        // converto FormData in un oggetto scenario che contiene tutti i valori del form
        const scenario = {};
        formData.forEach((value, key) => {
            scenario[key] = value;
        });

        fetch('http://localhost:8080/scenario/salva', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(scenario)
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
        })
            .catch(error => {
                console.log('Si è verificato un errore:', error);
            });
    });
});

