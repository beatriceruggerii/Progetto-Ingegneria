document.addEventListener('DOMContentLoaded', function (){
    console.log('start');
    document.getElementById('registrazione').addEventListener('submit', function(event) {
        console.log('inizio listener.');
        event.preventDefault();

        const usernameValue = document.getElementById('uname').value;
        const passwordValue = document.getElementById('pword').value;
        const confermaPassword = document.getElementById('cpword').value;
        console.log(usernameValue);
        console.log(passwordValue);
        console.log(confermaPassword);

        if (passwordValue === confermaPassword) {
            const user = {
                username: usernameValue,
                password: passwordValue
            };

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
                        window.location.href = '../index.html';
                    } else {
                        //openModal('utenteEsistente');
                        console.log('utente esistente.')
                    }
                })
                .catch(error => {
                    console.error('Si è verificato un errore:', error);
                });
        } else {
            //openModal('passwordMismatch');
            console.log('le password non combaciano');
        }
    });
});