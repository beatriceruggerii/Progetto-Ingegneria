document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaTitolo');
    const searchForm = document.getElementById('searchForm');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const titolo = searchInput.value;

        fetch(`http://localhost:8080/storie/titolo/${titolo}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella richiesta');
                }
                return response.json();
            })
            .then(data => {
                console.log("Dati ricevuti:", data);
                const listaFiltrataStoria = data.listaFiltrataStoria;
                mostraStorie(listaFiltrataStoria);
            })
            .catch(error => {
                console.error('Errore:', error);
            });
    });
});


