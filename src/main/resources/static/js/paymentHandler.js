document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("Premium");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Previeni il comportamento predefinito del form

        // Crea l'oggetto JSON da inviare
        const paymentData = {
            amount: parseFloat(document.getElementById("amount").value),
            card_holder: document.getElementById("card_holder").value,
            card_number: document.getElementById("card_number").value,
            expire_date: document.getElementById("expire_date").value,
            cvv: document.getElementById("cvv").value
        };

        // Effettua la richiesta POST
        fetch("http://localhost:8080/proxy/pay", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(paymentData)
        })
            .then(response => response.json())
            .then(data => {
                // Mostra la risposta del server
                console.log(data); // Aggiungi questa linea per controllare cosa restituisce il server
                if (data.status === "Autorizzato") {
                    alert(`Pagamento riuscito! Totale: €${data.total}, Commissione: €${data.fee}`);
                    console.log('Pagamento autorizzato. Reindirizzando alla homepage...');
                    window.location.replace("../homepage.html"); // Alternativa a href
                }
                else if (data.status === "Fallito") {
                    alert(`Pagamento fallito: ${data.message}`);
                }
                else if (data.status === "400") {
                    let errorMessage = `${data.message}:\n`;
                    for (const [field, message] of Object.entries(data.errors)) {
                        errorMessage += `- ${field}: ${message}\n`;
                    }
                    alert(errorMessage);
                }
                else {
                    alert('Server del pagamento Offline');
                }
            })
            .catch(error => {
                console.error("Errore durante il pagamento:", error);
                alert("Si è verificato un errore durante l'elaborazione del pagamento.");
            });
    });
});