document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("Premium");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Previene il comportamento predefinito del form

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
                if (data.status === "Autorizzato ") {
                    alert(`Pagamento riuscito! Totale: €${data.total}, Commissione: €${data.fee}`);
                } else {
                    alert(`Pagamento fallito: ${data.message}`);
                }
            })
            .catch(error => {
                console.error("Errore durante il pagamento:", error);
                alert("Si è verificato un errore durante l'elaborazione del pagamento.");
            });
    });
});
