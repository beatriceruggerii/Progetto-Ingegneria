document.getElementById('nuovoIndovinello').addEventListener('submit', function(event){
    event.preventDefault();

    const testo = document.getElementById('testoIndovinello').value;
    const soluzione = document.getElementById('testoSoluzione').value;
    const idMadre = document.getElementById('scenarioMadreIndovinello').value;
    const idFiglio = document.getElementById('scenarioFiglioIndovinello').value;

    if(idMadre === "--Scegli scenario madre--" || idFiglio === "--Scegli scenario figlio--"){
        console.log("Scenario non selezionato");
        alert("Scenario non selezionato");
    }
    else{
        const scelta = {
            testo: testo,
            idMadre: idMadre,
            idFiglio: idFiglio,
            soluzione: soluzione
        };

        fetch('http://localhost:8080/salva_indovinello', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify(scelta)
        }).then(response => {
            if(response.ok){
                alert('Indovinello creato e salvato');
            } else{
                alert('Indovinello non registrato');
            }
        }).catch(error => {
            console.log('errore', error);
        });
    }
})