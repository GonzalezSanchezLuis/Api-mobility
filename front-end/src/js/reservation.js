import {token} from "./helpers.js";
export function reservation() {
    let url = "http://localhost:8080/api/v1/reservation"; 
    let reservationForm = document.getElementById("reservation-form");

    if (reservationForm) {
        reservationForm.addEventListener("submit", e => {
            e.preventDefault();
            let originName = reservationForm.elements["originName"].value;
            let destinationName = reservationForm.elements["destinationName"].value;
            let dateAndHour = reservationForm.elements["dateAndHour"].value;

            let reservationData = {
                originName,
                destinationName,
                dateAndHour
            };
            console.log(reservationData);

            const token = localStorage.getItem('token');
            if (!token) {
                console.error('No token found');
                alert('Error: No token found');
                return;
            }
         
            fetch(url, {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                  },
                body: JSON.stringify(reservationData)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(response.status + ": " + text); });
                }
                return response.json();
            })
            .then(data => {
                console.log('Success:', data);
                displayReservationData(data);
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
                alert('Error: ' + error.message);
            });
        });
    }
}
export function getAllReservation(){
    let url = "http://localhost:8080/api/v1/reservations";

  token(url, {
    method: 'GET',
  })

  .then(reservations => {
    console.log('Success:', reservations);
    displayReservationData(reservations); // Actualizar la lista de ubicaciones en el frontend
  })
  .catch(error => {
    console.error('Error fetching data:', error);

  });

}

function displayReservationData(reservations) { 
    const resultDiv = document.getElementById('result-reservation');
    resultDiv.innerHTML = '';

    reservations.forEach(reser => {
        resultDiv.innerHTML += `    
                <div class="card">
                    <div class="card-body">
                    <p>Origin: ${reser.originName}</p>
                    <p>Destination: ${reser.destinationName}</p>
                    <p>Distance: ${reser.formattedDistance}</p>
                    <p>Duration: ${reser.formattedDuration}</p>
                    <p>Cost $: ${reser.formattedCost} COP</p>
                     <p>Date and time: ${reser.formattedDateAndTime}</p>
                    <a href="#" class="btn btn-danger"data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-id="${reser.reservationId}">Eliminar</a>
                <br>
                </div>
                </div>
                <br><br>  
        `;
        
    });
  }


// document.addEventListener('DOMContentLoaded', () => {
//     const reservationData = localStorage.getItem('reservationData');
//     if (reservationData) {
//       const reservation = JSON.parse(reservationData);
//       displayReservationData(reservation);
//     }
//   });


