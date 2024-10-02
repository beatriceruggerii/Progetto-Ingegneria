document.getElementById('nuovoIndovinello').addEventListener('submit', function (event) {
    event.preventDefault();

    const testo = document.getElementById('testoIndovinello').value;
    const soluzione = document.getElementById('testoSoluzione').value;
    const idMadre = document.getElementById('scenarioMadreIndovinello').value;
    const idFiglio = document.getElementById('scenarioFiglioIndovinello').value;

    if (validateScenari(idMadre, idFiglio)) {
        const scelta = {
            testo: testo,
            soluzione: soluzione,
            idMadre: idMadre,
            idFiglio: idFiglio
        };

        fetch('http://localhost:8080/salva_indovinello', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(scelta)
        }).then(response => {
            if (response.ok) {
                console.log("indovinello salvato");
                window.location.href = './crea_scenario.html';
            } else {
                //alert('Indovinello non registrato');

                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });


            }
        }).catch(error => {
            console.log('errore', error);
        });
    }
});

function validateScenari(idMadre, idFiglio) {
    if (idMadre === "" || idFiglio === "") {
        console.log("Scenario non selezionato");
        alert("Seleziona entrambi gli scenari prima di continuare.");
        return false;
    }
    return true;
}