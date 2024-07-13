package com.transportapi.model.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "reservation")
public class Reservation {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Schema(hidden = true)
   private Long reservationId;

   private LocalDateTime dateAndHour;

   @Schema(hidden = true)
   private double distance;

   @Schema(hidden = true)
   private int estimatedDuration;

   private String originName;

   @Schema(hidden = true)
   private double originLatitude;

   @Schema(hidden = true)
   private double originLongitude;

   private String destinationName;

   @Schema(hidden = true)
   private double destinationLatitude;

   @Schema(hidden = true)
   private double destinationLongitude;

   @Schema(hidden = true)
   private double cost;


   @Transient
   @Schema(hidden = true)
   private String formattedDistance;

   @Transient
   @Schema(hidden = true)
   private String formattedDuration;

   @Transient
   @Schema(hidden = true)
   private String formattedCost;

   @Transient
   @Schema(hidden = true)
   private String formattedDateAndTime;


   @ManyToOne
   @JoinColumn(name = "user_id")
   @Schema(hidden = true)
   private User user;


}
