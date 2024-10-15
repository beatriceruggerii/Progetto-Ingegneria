document.addEventListener("DOMContentLoaded", function (event) {
        const urlParams = new URLSearchParams(window.location.search);
        const titoloStoria = decodeURIComponent(urlParams.get('titoloStoria'));

        if (titoloStoria) {
            fetch(`fetch_dati_storia/${titoloStoria}`)
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
                mostraFormModifica(data);
            })
                .catch(error => console.error("Errore:", error));
        }
    }
)