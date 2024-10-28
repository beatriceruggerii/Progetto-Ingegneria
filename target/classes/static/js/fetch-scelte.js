fetch('http://localhost:8080/scelte/')
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

            //inserimento delle scelte già create nell'elenco (crea_scenario.html)
            let areaScelte = document.getElementById("areaScelte");
            // Costruisco il contenuto HTML con gli scenari ricevuti
            let html = '';
            scelte.forEach(scelta => {
                html +=
                    "<li class=\"list-group-item\">\n" +
                    "<strong>" + scelta.descrizione +"</strong>\n" +
                    "<p> Da <b>" + scelta.scenarioMadre.titolo +"</b> a <b> "+  scelta.scenarioFiglio.titolo +" </b></p>"+
                    "</li>";
            });
            areaScelte.innerHTML = html;
        }
    })
    .catch(error => {
        console.error('Errore:', error);
    });

