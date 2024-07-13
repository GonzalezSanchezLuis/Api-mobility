

import { singup }from './singup.js';
singup();

import { login} from './login.js';
login();


import { location,  getAllLocations } from './route.js';
location();
getAllLocations();

import { reservation, getAllReservation  } from './reservation.js';
reservation();
getAllReservation();

import { deleteReservation } from './deleteReservation.js';
deleteReservation();

import { logout } from './logout.js';
logout();

import { getLocationById, getLocationIdFromURL, update } from './updateRoute.js';
document.addEventListener("DOMContentLoaded", function() {
    const locationId = getLocationIdFromURL(); 

    if (locationId) {
        getLocationById(locationId);
        update(locationId);
    }
});

import { deleteLocation } from './deleteRoute.js';
deleteLocation();