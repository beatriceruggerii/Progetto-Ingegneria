document.addEventListener("DOMContentLoaded", function (){
    fetch('http://localhost:8080/security')
        .then(response => {
            if (!response.ok) {
                window.location.href = '/';     // Redirigi alla pagina login
            }
        })
        .catch(error => console.error('Errore:', error));
});