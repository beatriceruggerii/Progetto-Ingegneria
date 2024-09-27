document.addEventListener('DOMContentLoaded',function () {
        document.getElementById("nuovaStoria").addEventListener("submit", function (){
            event.preventDefault();

            var titolo = document.getElementById('titolo').value;

            fetch('http://localhost:8080/crea_storia', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: titolo
            }).then(response => {
                console.log("richiesta inviata");
                if (response.ok) {
                    // Se la richiesta è andata a buon fine, puoi fare qualcosa, ad esempio reindirizzare l'utente a un'altra pagina
                    window.location.href = './crea_scenario.html';
                } else {
                    throw new Error('Errore nella richiesta');
                }
            })
                .catch(error => {
                    console.log('Si è verificato un errore:', error);
                });
        })
    }
);