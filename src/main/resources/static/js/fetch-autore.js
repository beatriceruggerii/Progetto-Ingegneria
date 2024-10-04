document.addEventListener("DOMContentLoaded", function () {
    fetchAutori();

    function fetchAutori() {
        fetch('http://localhost:8080/fetch_autori')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella richiesta');
                }
                return response.json();
            })
            .then(data => {
                console.log("richiesta fetch scenari inviata");
                if (data && data.listaAutori) {
                    const autori = data.listaAutori;

                    // Per caricare gli scenari nelle select (crea_scenario.html)
                    let elements = document.getElementsByClassName("elenco-autoi");
                    if (elements != null) {
                        for (let autore of autori) {
                            //console.log(scenario.titolo);
                            let option = document.createElement('option');
                            option.textContent = autore.username;
                            option.value = autore.username;
                            console.log("Valore aggiunto all'opzione: " + option.value);
                            for (let i = 0; i < elements.length; i++) {
                                elements[i].appendChild(option.cloneNode(true));  // Cloniamo l'opzione
                            }
                        }
                    }

                }
            })
            .catch(error => {
                console.error('Errore:', error);
            });
    }
});

