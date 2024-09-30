document.getElementById("logout").addEventListener("click", function (event){
    event.preventDefault();

    fetch("/logout")
        .then(response => {
            if (response.ok) {
                // Redirect l'utente alla pagina di login
                window.location.href = '../index.html';
            } else {
                console.error('Errore durante il logout:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Errore durante il logout:', error);
        });
})