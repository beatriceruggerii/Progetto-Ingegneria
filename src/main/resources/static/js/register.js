document.addEventListener('DOMContentLoaded', function () {
    console.log('start');
    document.getElementById('registrazione').addEventListener('submit', function (event) {
        console.log('inizio listener.');
        event.preventDefault();

        const formData = new FormData(event.target);
        //debug
        console.log("check premium: "+ formData.get("premium") );

        // converto FormData in un oggetto user che contiene tutti i valori del form
        const user = {};
        if (formData.get("pword") === formData.get("cpword")) {
            user["username"] = formData.get("uname");
            user["password"] = formData.get("pword");

            fetch('http://localhost:8080/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            })
                .then(response => {
                    if (response.ok) {
                        //openModal('registrazioneAvvenuta');
                        console.log('registrazione avvenuta.');
                        if(formData.get("premium") === "premium") {
                            //reindirizzo alla pagina di pagamento
                            window.location.href = '../PassaPremium.html';
                        } else{
                            window.location.href = '../homepage.html';
                        }
                    } else{
                        $('#utenteEsistente').modal('show');
                        console.log('utente esistente.')
                    }
                })
                .catch(error => {
                    console.error('Si è verificato un errore:', error);
                });
        } else {
            $('#passwordMismatch').modal('show');
            console.log('le password non combaciano');
        }
    });
});