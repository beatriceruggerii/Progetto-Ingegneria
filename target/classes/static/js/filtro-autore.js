document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaAutore');
    const searchForm = document.getElementById('searchFormAutore');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const autore = searchInput.value;

        fetch('http://localhost:8080/filtro-autore', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(autore)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella richiesta');
                }
                return response.json();
            })
            .then(data => {
                console.log("Dati ricevuti:", data);
                const listaFiltrataAutore = data.listaFiltrataStoria;
                mostraStorie(listaFiltrataAutore);
            })
            .catch(error => {
            console.error('Errore:', error);
            });
    });
});
