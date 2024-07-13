import { token} from './helpers.js'; 

export function location() {
  let url = "http://localhost:8080/api/v1/route/save"; 
  let locationForm = document.getElementById("location");

  if (locationForm) {
    locationForm.addEventListener("submit", e => {
      e.preventDefault();
      let originName = locationForm.elements["origen"].value;
      let destinationName = locationForm.elements["destino"].value;

      let locationData = {
        originName,
        destinationName,
      };

      token(url, {
        method: 'POST',
        body: JSON.stringify(locationData)
      })
      .then(data => {
        console.log('Success:', data);
        alert('Location saved successfully!');
      })
      .catch(error => {
        console.error('There was a problem with your fetch operation:', error);
        alert('Error: ' + error.message);
      });
    });
  }
}

export function getAllLocations() {
  let url = "http://localhost:8080/api/v1/route/routes";

  token(url, {
    method: 'GET',
  })
  .then(locations => {
    // console.log('Success:', locations);
    updateLocationList(locations); // Actualizar la lista de ubicaciones en el frontend
  })
  .catch(error => {
    // console.error('Error fetching data:', error);

  });
}

function updateLocationList(locations) {
  const locationList = document.getElementById('result');
  locationList.innerHTML = '';

  locations.forEach(location => {
    locationList.innerHTML += `
        <div class="card">
          <div class="card-body">
             <p>Origin: ${location.originName}</p>
            <p>Destination: ${location.destinationName}</p>
            <p>Distance: ${location.formattedDistance}</p>
            <p>Duration: ${location.formattedDuration}</p>
            <p>Cost $: ${location.formattedCost} COP</p>
            <a href="./update.html?id=${location.locationId}" class="btn btn-primary" >Editar</a>
            <a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-id=${location.locationId}>Eliminar</a>
            <br>
          </div>
        </div>
    <br><br>
    `

  })
}




