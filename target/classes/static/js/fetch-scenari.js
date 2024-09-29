fetch('http://localhost:8080/fetch_scenari')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch scenari inviata");
        if (data && data.listaScenari) {
            const scenari = data.listaScenari;
            let elements = document.getElementsByClassName("elenco-scenari");

            for (let scenario of scenari) {
                console.log(scenario.titolo);
                let option = document.createElement('option');
                option.textContent = scenario.titolo;
                let objScenario = JSON.stringify(scenario);
                option.value = objScenario;
                for (let i = 0; i < elements.length; i++) {
                    elements[i].appendChild(option.cloneNode(true));  // Cloniamo l'opzione
                }
            }
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

