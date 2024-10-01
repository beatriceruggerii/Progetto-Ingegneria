fetch('http://localhost:8080/fetch_scelte')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch scelte inviata");
        if (data && data.listaScelte) {
            const scelte = data.listaScelte;

            const areaScelte = document.getElementById("areaScelte");
            // Costruisco il contenuto HTML con gli scenari ricevuti
            let html = '';
            scelte.forEach(scelta => {
                html += `
                    <div class="card mt-3">
                        <div class="card-header">
                            <h4>Scelta: `+ scelta.descrizione +`</h4>
                        </div>
                        <div class="card-body"> 
                            <p> Da `+scelta.scenarioMadre.titolo+` a `+scelta.scenarioFiglio.titolo+` </p>
                        </div>
                    </div>`;
            });
            areaScelte.innerHTML = html;
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

