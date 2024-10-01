document.getElementById('nuovaScelta').addEventListener('submit', function(event){
    event.preventDefault();

    const testo = document.getElementById('testoScelta').value;
    const idMadre = document.getElementById('scenarioMadreScelta').value;
    const idFiglio = document.getElementById('scenarioFiglioScelta').value;

    if(idMadre === "--Scegli scenario madre--" || idFiglio === "--Scegli scenario figlio--"){
        console.log("Scenario non selezionato");
        alert("Scenario non selezionato");
    }
    else{
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
                alert('Scelta non registrata');
            }
        }).catch(error => {
            console.log('errore', error);
        });
    }
});
