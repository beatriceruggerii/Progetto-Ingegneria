document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("nuovoOggetto").addEventListener("submit", function (event) {
        event.preventDefault();


            const formData = new FormData(event.target);

            // converto FormData in un oggetto scenario che contiene tutti i valori del form
            const oggetto = {};
            console.log("Oggetto da salvare: ")
            formData.forEach((value, key) => {
                oggetto[key] = value;
                console.log(key + ": " + value);
            });

        if(validateScenari(oggetto["scenarioMadreOggetto"],oggetto["scenarioControlloreOggetto"])) {
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
                    return response.text().then(errorMessage => {
                        document.getElementById("errorMessage").textContent = errorMessage;
                        $('#errorModal').modal('show'); // Mostra il modal
                    });
                }
            })
                .catch(error => {
                    console.log('Si è verificato un errore:', error);
                });
        }
    });
});

function validateScenari(idMadre, idFiglio){
    if(idMadre === "" || idFiglio === ""){
        console.log("Scenario non selezionato");
        alert("Seleziona entrambi gli scenari prima di continuare.");
        return false;
    }
    return true;
}