document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaAutore');
    const searchForm = document.getElementById('searchFormAutore');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const autore = searchInput.value;
        fetch(`http://localhost:8080/storie/autore/${autore}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella richiesta');
                }
                return response.json();
            })
            .then(data => {
                console.log("Dati ricevuti:", data);
                const listaFiltrataAutore = data.listaStorieUtenteDTOs;
                mostraStorie(listaFiltrataAutore);
            })
            .catch(error => {
            console.error('Errore:', error);
            });
    });
});
