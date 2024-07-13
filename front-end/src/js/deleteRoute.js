import {token} from "./helpers.js";
export function deleteLocation(){
    document.addEventListener('DOMContentLoaded', (event) => {
        const exampleModal = document.getElementById('exampleModal');
        exampleModal.addEventListener('show.bs.modal', function (event) {
          // Botón que activó el modal
          const button = event.relatedTarget;
          // Extraer información de los atributos data-bs-*
          const locationId = button.getAttribute('data-bs-id');
    
          // Actualizar el contenido del modal
          const confirmDeleteButton = document.getElementById('confirmDeleteButton');
          confirmDeleteButton.setAttribute('data-id', locationId);
        });
    
        document.getElementById('confirmDeleteButton').addEventListener('click', function () {
          const locationId = this.getAttribute('data-id');
          deleteLocationById(locationId);
        });
      });
    
      function deleteLocationById(locationId) {
        const url = `http://localhost:8080/api/v1/route/delete/${locationId}`;
    
        token(url, {
          method: 'DELETE'
        })
          .then(response => {
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
            console.log('Location deleted successfully');
            alert('Ubicación eliminada con éxito');
            // Opcional: Cerrar el modal manualmente
            const exampleModal = new bootstrap.Modal(document.getElementById('exampleModal'));
            exampleModal.hide();
            // Opcional: Actualizar la vista después de la eliminación
            // loadLocations(); // Debes tener una función para cargar las ubicaciones nuevamente
          })
          .catch(error => {
            console.error('Error deleting location:', error);
            alert('Error: ' + error.message);
          });
      }
}