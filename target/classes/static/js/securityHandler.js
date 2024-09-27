document.addEventListener("DOMContentLoaded", function (){
    fetch('http://localhost:8080/security')
        .then(data => {
            if (response.ok) {
                window.location.href = './homepage';  // Redirigi alla homepage
            } else {
                window.location.href = '/';     // Redirigi alla pagina login
            }
        })
        .catch(error => console.error('Errore:', error));
});