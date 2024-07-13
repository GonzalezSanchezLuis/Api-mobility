package com.transportapi.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long locationId;

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



    @ManyToOne
    @Schema(hidden = true)
    @JoinColumn(name = "user_id")
    private User user;


}
