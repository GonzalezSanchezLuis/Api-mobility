package com.transportapi.services;

import java.security.Principal;
import java.util.List;
import com.transportapi.model.entity.Reservation;

public interface ReservationInterface {
    Reservation registerReservation(Principal pricipal, Reservation trip);
    List<Reservation> getAllReservations();
    void deleteReservation(Long id);
}
