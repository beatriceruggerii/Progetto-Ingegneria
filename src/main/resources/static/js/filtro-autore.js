document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaAutore');
    const searchForm = document.getElementById('searchFormAutore');

    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();
        console.log("Richiesta di filtro per autore");

        if (window.storiaDTOS && window.storiaDTOS.length > 0) {
            const searchValue = searchInput.value.toLowerCase();
            const filteredStories = window.storiaDTOS.filter(storiaDTO => storiaDTO.username.toLowerCase().includes(searchValue)
            );
            mostraStorie(filteredStories);  // Visualizza le storie filtrate
        } else {
            console.log("Nessuna storia disponibile o fetch non completato.");
        }
    });
});
