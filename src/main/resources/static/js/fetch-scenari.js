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

            // Per caricare gli scenari nelle select (crea_scenario.html)
            let elements = document.getElementsByClassName("elenco-scenari");
            if(elements != null) {
                for (let scenario of scenari) {
                    //console.log(scenario.titolo);
                    let option = document.createElement('option');
                    option.textContent = scenario.titolo;
                    option.value = scenario.id;
                    console.log("Valore aggiunto all'opzione: " + option.value);
                    for (let i = 0; i < elements.length; i++) {
                        elements[i].appendChild(option.cloneNode(true));  // Cloniamo l'opzione
                    }
                }
            }

            // Per mostrare gli scenari già creati (crea_scenario.html)
            let areaScenari = document.getElementById("areaScenari");
            if(areaScenari != null) {
                // Costruisco il contenuto HTML con gli scenari ricevuti
                let html = '';
                scenari.forEach(scenario => {
                    html +=
                        "<li class=\"list-group-item\">\n" +
                        "<strong>" + scenario.titolo +"</strong>\n" +
                        "<p>" +  scenario.testo + "</p>";
                    "</li>";

                });
                areaScenari.innerHTML = html;
            }
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

