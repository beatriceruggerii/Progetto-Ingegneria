fetch('http://localhost:8080/fetch_oggetti')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch oggetti inviata");
        if (data && data.listaOggetti) {
            if (document.getElementById("areaOggetti") != null) {
                console.log("elenco oggetti trovato");
                const oggetti = data.listaOggetti;
                let elenco = document.getElementById("areaOggetti");

                for (let oggetto of oggetti) {
                    console.log(oggetto.nomeOggetto);
                    console.log(oggetto.toString());
                    elenco.innerHTML += "<li class=\"list-group-item\">\n" +
                        "            <strong>" + oggetto.nomeOggetto +"</strong>\n" +
                        "                            <p> Acquisibile in <b>" + oggetto.scenarioMadre.titolo + "</b>, per accedere a <b>" + oggetto.scenarioControllore.titolo + "</b> </p>\n" +
                        "        </li>";
                }
            }
        }

    })
    .catch(error => {
        console.error('Errore:', error);
    });