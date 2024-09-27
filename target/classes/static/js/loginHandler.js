document.addEventListener("DOMContentLoaded", function (){

    document.getElementById('login').addEventListener('submit', function(event) {
        event.preventDefault();

        const usernameValue = document.getElementById('uname').value;
        const passwordValue = document.getElementById('pword').value;

        const user = {
            username: usernameValue,
            password: passwordValue,
        };
        console.log(user);

        fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
            .then(response => {
                if (response.ok) {
                    // Se la richiesta è andata a buon fine, puoi fare qualcosa, ad esempio reindirizzare l'utente a un'altra pagina
                    window.location.href = 'Homepage.html';
                } else {
                    $('#credenzialiErrate').modal('show'); // Mostra il modal
                    throw new Error('Errore nella richiesta');
                }
            })
            .catch(error => {
                console.error('Si è verificato un errore:', error);
            });
    });

});