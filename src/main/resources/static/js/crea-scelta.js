document.getElementById('nuovaScelta').addEventListener('submit', function(event){
    event.preventDefault();

    const testo = document.getElementById('testoScelta').value;
    const idMadre = document.getElementById('scenarioMadreScelta').value;
    const idFiglio = document.getElementById('scenarioFiglioScelta').value;


    if(validateScenari(idMadre,idFiglio)){
        const scelta = {
            testo: testo,
            idMadre: idMadre,
            idFiglio: idFiglio
        };

        fetch('http://localhost:8080/salva_scelta', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(scelta)
        }).then(response => {
            if(response.ok){
                window.location.href = './crea_scenario.html';
            } else{
                //alert('Scelta non registrata');
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

function validateScenari(idMadre, idFiglio){
    if(idMadre === "" || idFiglio === ""){
        console.log("Scenario non selezionato");
        alert("Seleziona entrambi gli scenari prima di continuare.");
        return false;
    }
    return true;
}
