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
                //console.log(scenario.titolo);
                let option = document.createElement('option');
                option.textContent = scenario.titolo;
                option.value = scenario.id;
                console.log("Valore aggiunto all'opzione: "+ option.value);
                for (let i = 0; i < elements.length; i++) {
                    elements[i].appendChild(option.cloneNode(true));  // Cloniamo l'opzione
                }
            }
            const areaScenari = document.getElementById("areaScenari");
            // Costruisco il contenuto HTML con gli scenari ricevuti
            let html = '';
            scenari.forEach(scenario => {
                html += `
                        <div class="card mt-3">
                            <div class="card-header">
                                <h4>${scenario.titolo}</h4>
                            </div>
                            <div class="card-body">
                                <p>${scenario.testo}</p>
                            </div>
                        </div>`;
            });

            areaScenari.innerHTML = html;
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

