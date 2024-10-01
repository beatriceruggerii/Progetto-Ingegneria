fetch('http://localhost:8080/fetch_indovinelli')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    })
    .then(data => {
        console.log("richiesta fetch indovinelli inviata");
        if (data && data.listaIndovinelli) {
            if (document.getElementById("areaIndovinelli") != null) {
                const indovinelli = data.listaIndovinelli;
                let elenco = document.getElementById("areaIndovinelli");

                for (let indovinello of indovinelli) {
                    console.log(indovinello.toString());
                    elenco.innerHTML += "<li class=\"list-group-item\">\n" +
                        "            <strong>" + indovinello.descrizione +"</strong>\n" +
                        "            <p>Risposta corretta: "+ indovinello.rispostaCorretta + " </p>" +
                        "            <p> Da <b>" + indovinello.scenarioMadre.titolo + "</b> a <b>" + indovinello.scenarioFiglio.titolo + "</b> </p>\n" +
                        "            </li>";
                }
            }
        }

    })
    .catch(error => {
        console.error('Errore:', error);
    });

