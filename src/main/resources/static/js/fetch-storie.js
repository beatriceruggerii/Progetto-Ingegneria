fetch('http://localhost:8080/fetch_storie')
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella richiesta');
        }
        return response.json();
    }).then(data => {
    console.log("richiesta fetch storie inviata");
    console.log("Dati ricevuti:", data); // Aggiungi questo per verificare i dati ricevuti
    if (data && data.storiaDTOS) {
        const storieDTOS = data.storiaDTOS;

        //inserimento delle storie nel catalogo (catalogo.html)
        let catalogo = document.getElementById("catalogo");

        let html = '';
        storieDTOS.forEach(storiaDTO => {
            html +=
                "<li class=\"list-group-item d-flex justify-content-between align-items-center\">\n" +
                "<div class=\"col-md-8\">"+
                "<h3 class=\"fw-bold text-start\">Titolo: " + storiaDTO.titolo +"</h3>\n" +
                "<p class=\"text-muted text-start\">Descrizione: "+ storiaDTO.descrizioneIniziale +"</p>"+
                "</div>"+
                "<div class=\"col-md-4 text-end\">"+
                "<input type=\"submit\" class=\"btn btn-custom bs-tooltip-end\" value=\"Gioca\">"+
                "</div>"+
                "</li>";
        });
        catalogo.innerHTML = html;
    }
})
    .catch(error => {
        console.error('Errore:', error);
    });