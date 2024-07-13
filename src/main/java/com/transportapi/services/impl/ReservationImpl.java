package com.transportapi.services.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.transportapi.services.impl.geocoding.GeocodingImpl;
import com.transportapi.util.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.transportapi.model.dao.ReservationDao;
import com.transportapi.model.dao.UserDao;
import com.transportapi.model.entity.Reservation;
import com.transportapi.model.entity.User;
import com.transportapi.services.ReservationInterface;


@Service
public class ReservationImpl implements ReservationInterface {
    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GeocodingImpl geocodingImpl;

    private static final double EARTH_RADIUS = 6371;
    private static final double COST_PER_KILOMETER = 2000;
    private static final double EXCHANGE_RATE_USD_TO_COP = 3500;


    @Override
    public Reservation registerReservation(Principal principal, Reservation reservation) {
        String username = principal.getName();
        User user = userDao.findUserByEmail(username);


        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el correo electrónico: " + username);
        }

        reservation.setUser(user);

        // Validar que las direcciones no sean nulas
        if (reservation.getOriginName() == null || reservation.getDestinationName() == null) {
            throw new IllegalArgumentException("Las direcciones de origen y destino no pueden ser nulas");
        }

        double[] originCoordinates = geocodingImpl.getCoordinates(reservation.getOriginName());
        double[] destinationCoordinates = geocodingImpl.getCoordinates(reservation.getDestinationName());

        if (originCoordinates == null || destinationCoordinates == null) {
            throw new IllegalArgumentException("No se pudieron obtener las coordenadas para las direcciones proporcionadas.");
        }

        reservation.setOriginLatitude(originCoordinates[0]);
        reservation.setOriginLongitude(originCoordinates[1]);
        reservation.setDestinationLatitude(destinationCoordinates[0]);
        reservation.setDestinationLongitude(destinationCoordinates[1]);

        double distance = calculateDistance(originCoordinates[0], originCoordinates[1], destinationCoordinates[0], destinationCoordinates[1]);
        double cost = calculateCost(distance);
        int estimatedDuration = calculateEstimatedDuration(distance);

        // Convertir el costo a pesos colombianos
        double costInColombianPesos = convertCostToColombianPesos(cost);
        reservation.setCost(costInColombianPesos);

        // Formatear los valores antes de guardarlos
        reservation.setDistance(distance);
        reservation.setEstimatedDuration(estimatedDuration);
        reservation.setFormattedDistance(RouteUtils.formatDistance(reservation.getDistance()));
        reservation.setFormattedDuration(RouteUtils.formatDuration(reservation.getEstimatedDuration()));
        reservation.setFormattedCost(RouteUtils.formatCost(reservation.getCost()));
        // Guardar la ubicación en la base de datos
        reservation = reservationDao.save(reservation);

        LocalDateTime date = reservation.getDateAndHour();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String readableDateAndTime = date.format(formatter);
        reservation.setFormattedDateAndTime(readableDateAndTime);

        return reservation;

    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = (List<Reservation>) reservationDao.findAll();
        reservations.forEach(reservation -> {
            reservation.setFormattedDistance(RouteUtils.formatDistance(reservation.getDistance()));
            reservation.setFormattedDuration(RouteUtils.formatDuration(reservation.getEstimatedDuration()));
            reservation.setFormattedCost(RouteUtils.formatCost(reservation.getCost()));

            // Formatear la fecha y hora solo si no es nula
            LocalDateTime reservationDateTime = reservation.getDateAndHour();
            if (reservationDateTime != null) {
                String formattedDateAndTime = formatDateTime(reservationDateTime);
                reservation.setFormattedDateAndTime(formattedDateAndTime);
            } else {
                reservation.setFormattedDateAndTime("Fecha no disponible");
            }
        });
        return reservations;
    }





    @Override
    public void deleteReservation(Long id) {
        try {
            reservationDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting location", e);
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private double calculateCost(double distance) {
        return distance * COST_PER_KILOMETER;
    }

    private int calculateEstimatedDuration(double distance) {
        double averageSpeedKmPerHour = 50.0;
        double hours = distance / averageSpeedKmPerHour;
        return (int) Math.round(hours * 60);
    }

    private double convertCostToColombianPesos(double costInUSD) {
        return costInUSD * EXCHANGE_RATE_USD_TO_COP;
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
    
}
