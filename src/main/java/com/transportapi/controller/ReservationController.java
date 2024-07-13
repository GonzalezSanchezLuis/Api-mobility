package com.transportapi.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transportapi.model.entity.Reservation;
import com.transportapi.services.ReservationInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.transportapi.documentation.ApiDocumentationReservation;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
@Tag(name = "Reservation", description = "Controlador de viajes")
public class ReservationController {
    @Autowired
    private ReservationInterface reservationService;


    @Operation(summary = "Reservar un viaje",description = " Recibe una dirección de origen  destino y una para reservar  un viaje", tags = {"Reservation"})
    @ApiDocumentationReservation.TripApiResponses
    @PostMapping("reservation")
    public ResponseEntity<Reservation> registerReservation(Principal principal, @RequestBody Reservation reservation){
        try {
            Reservation savedReservation = reservationService.registerReservation(principal, reservation);

            System.out.println("Distancia formateada: " + savedReservation.getFormattedDistance());
            System.out.println("Duración formateada: " + savedReservation.getFormattedDuration());
            System.out.println("Hora formateado: " + savedReservation.getFormattedDateAndTime());

            return ResponseEntity.status(201).body(savedReservation);

        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de errores para ayudar a la depuración
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Obtiene todos los viajes reservados registrados por el usuario en la base de datos",description = "Obtener todos los viajes reservados registrados en la base de datos", tags = {"Reservation"})
    @ApiDocumentationReservation.TripApiResponses
    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>>getAllReservations(){
        List<Reservation> reservation = reservationService.getAllReservations();
        if(reservation.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservation);

    }

    @Operation(summary = "Elimina un viaje reservado registrado en la base de datos",description = "Elimina un viaje reservado registrado en la base de datos", tags = {"Reservation"})
    @ApiDocumentationReservation.TripApiResponses
    @DeleteMapping("reservation-delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        try{
            reservationService.deleteReservation(id);
            return ResponseEntity.ok().body("Information deleted successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting information");
        }

    }
}
