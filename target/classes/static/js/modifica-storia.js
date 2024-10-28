// Recupero i dati relativi alla storia da modificare

document.addEventListener("DOMContentLoaded", function (event) {
        const urlParams = new URLSearchParams(window.location.search);
        const titoloStoria = decodeURIComponent(urlParams.get('titoloStoria'));

        if (titoloStoria) {
            fetch(`http://localhost:8080/storia/${titoloStoria}`)
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(errorMessage => {
                            document.getElementById("errorMessage").textContent = errorMessage;
                            $('#errorModal').modal('show'); // Mostra il modal
                        });
                    }
                    return response.json();
                }).
            then(data => {
                document.getElementById("titoloStoria").textContent = data.storia.titolo;
                console.log("Dati ricevuti sulla storia: ");
                console.log(data);
                mostraFormModifica(data);
            })
                .catch(error => console.error("Errore:", error));
        }
    }
)



// Popolo il documento con i form di modifica
function mostraFormModifica(data) {
    let titolo = data.storia.titolo;
    document.getElementById("titoloStoria").textContent = titolo;

    let scenari = data.scenari;
    let scelte = data.scelte;
    let indovinelli = data.indovinelli;
    let oggetti = data.oggetti;

    //debug
    console.log(scelte);

    for (scenario of scenari) {

        //creazione card di modifica dello scenario
        document.getElementById("elencoScenari").innerHTML += "" +
            " <div class=\"card mt-3\">" +
            "<div class=\"card-header text-center\">\n" +
            "                    <h3>" + scenario.titolo + " </h3>\n" +
            "                </div>\n" +
            "                <form onsubmit='modificaScenario(" + scenario.id + ", event)'>" +
            "                    <div class=\"card-body\">\n" +
            "                        <div class=\"form-group\">\n" +
            "                            <label>Descrizione</label>\n" +
            "                            <input type=\"text\" class=\"form-control\" name=\"testo\"\n" +
            "                                  value='" + scenario.testo + "'required>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"card-footer text-center\">\n" +
            "                        <button type=\"submit\" class=\"btn custom-btn\">Modifica scenario</button>\n" +
            "                    </div>\n" +
            "                </form>" +
            "</div>";
    }


    //modifica delle scelte
    let count = 0;
    for (scelta of scelte) {
        count = count + 1;
        document.getElementById("elencoScelte").innerHTML += "<div class=\"card mt-3\">\n" +
            "                <div class=\"card-header text-center\">\n" +
            "                    <h3>Scelta " + count + "</h3>\n" +
            "                </div>\n" +
            "                <form onsubmit='modificaScelta(" + scelta.id + ", event)'>\n" +
            "                    <div class=\"card-body\">\n" +
            "                        <div class=\"form-group\">\n" +
            "                            <label>Testo scelta</label>\n" +
            "                            <input type=\"text\" class=\"form-control\" name='nuovaDescrizione'\n" +
            "                                   value='" + scelta.descrizione + "' required>\n" +
            "                        </div>\n" +
            "                        <p>Da <b>" + scelta.scenarioMadre.titolo + " </b> a <b>" + scelta.scenarioFiglio.titolo + "</b></p>" +
            "                    </div>\n" +
            "                    <div class=\"card-footer text-center\">\n" +
            "                        <button type=\"submit\" class=\"btn custom-btn\">Modifica scelta</button>\n" +
            "                    </div>\n" +
            "                </form>\n" +
            "            </div>";

    }

    //modifica degli indovinelli
    count = 0;
    for (indovinello of indovinelli) {
        count = count + 1;
        document.getElementById("elencoIndovinelli").innerHTML += "<div class=\"card mt-3\">\n" +
            "                <div class=\"card-header text-center\">\n" +
            "                    <h3>Indovinello " + count + "</h3>\n" +
            "                </div>\n" +
            "                <form onsubmit='modificaIndovinello(" + indovinello.id + ", event)'>\n" +
            "                    <div class=\"card-body\">\n" +
            "                        <div class=\"form-group\">\n" +
            "                            <label>Testo indovinello</label>\n" +
            "                            <input type=\"text\" class=\"form-control\" name='nuovaDescrizione'\n " +
            "                                   value='" + indovinello.descrizione + "' required>\n" +
            "                        </div>\n" +
            "                        <div class=\"form-group\">\n" +
            "                            <label>Risposta esatta</label>\n" +
            "                            <input type=\"text\" class=\"form-control\" name='nuovaRispostaCorretta'\n " +
            "                                   value=" + indovinello.rispostaCorretta + " required>\n" +
            "                        </div>\n" +
            "                        <p>Da <b>" + indovinello.scenarioMadre.titolo + " </b> a <b>" + indovinello.scenarioFiglio.titolo + "</b></p>" +
            "                    </div>\n" +
            "                    <div class=\"card-footer text-center\">\n" +
            "                        <button type=\"submit\" class=\"btn custom-btn\">Modifica indovinello</button>\n" +
            "                    </div>\n" +
            "                </form>\n" +
            "            </div>";

    }
}

// Funzioni di modifica che inviano i dati la backend

function modificaScenario(idScenario, event) {
    event.preventDefault();

    // Seleziona il form e raccogli i dati da inviare
    const formData = new FormData(event.target);
    const scenarioData = {
        id: idScenario,
        testo: formData.get('testo')
    };


    // Esegui la richiesta PUT al server per modificare lo scenario
    fetch(`/modifica/${idScenario}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(scenarioData)
    })
        .then(response => {
            if (response.ok) {
                return response.text().then(message => {
                    document.getElementById("successMessage").textContent = message;
                    $('#successModal').modal('show'); // Mostra il modal
                });
            }else {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
        })
        .catch(error => console.error("Errore nella modifica dello scenario:", error));
}

function modificaScelta(idScelta, event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const sceltaData = {
        id: idScelta,
        testo: formData.get('nuovaDescrizione')
    };

    fetch(`/scelta/modifica/${idScelta}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(sceltaData)
    })
        .then(response => {
            if (response.ok) {
                return response.text().then(message => {
                    document.getElementById("successMessage").textContent = message;
                    $('#successModal').modal('show'); // Mostra il modal
                });
            }else {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
        })
        .catch(error => console.error("Errore nella modifica della scelta:", error));
}

function modificaIndovinello(idIndovinello, event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const indovinelloData = {
        id: idIndovinello,
        testo: formData.get('nuovaDescrizione'),
        soluzione: formData.get('nuovaRispostaCorretta')
    };

    //debug
    console.log("dati mandati:\n");
    console.log(idIndovinello);
    console.log(formData.get('nuovaDescrizione'));
    console.log(formData.get('nuovaRispostaCorretta'));

    fetch(`indovinello/modifica/${idIndovinello}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(indovinelloData)
    })
        .then(response => {
            if (response.ok) {
                return response.text().then(message => {
                    document.getElementById("successMessage").textContent = message;
                    $('#successModal').modal('show'); // Mostra il modal
                });
            }else {
                return response.text().then(errorMessage => {
                    document.getElementById("errorMessage").textContent = errorMessage;
                    $('#errorModal').modal('show'); // Mostra il modal
                });
            }
        })
        .catch(error => console.error("Errore nella modifica dell'indovinello:", error));
}