document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('cercaTitolo');
    const searchForm = document.getElementById('searchForm');
    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();
        console.log("Richiesta di filtro");

        if (window.storiaDTOS && window.storiaDTOS.length > 0) {  // Assicurati che window.storieDTOS sia disponibile
            const searchValue = searchInput.value.toLowerCase();
            const filteredStories = window.storiaDTOS.filter(storiaDTO =>
                storiaDTO.titolo.toLowerCase().includes(searchValue)
            );
            mostraStorie(filteredStories);  // Visualizza le storie filtrate
        } else {
            console.log("Nessuna storia disponibile o fetch non completato.");
        }
    });
});

