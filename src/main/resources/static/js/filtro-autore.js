document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaAutore');
    const searchForm = document.getElementById('searchFormAutore');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        fetch('http://localhost:8080/login')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella richiesta');
                }
                return response.json();
            })
            .then(data => {
                console.log("Dati ricevuti:", data);
                const listaFiltrataAutore = data.listaFiltrataAutore;
                if(listaFiltrataAutore.length > 0 ){
                    mostraStorie(listaFiltrataAutore);
                }else {
                    console.log("Nessuna storia disponibile o fetch non completato.");
                }
            });
    });
});
