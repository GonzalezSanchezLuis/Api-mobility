package com.transportapi.controller;

import java.security.Principal;
import java.util.List;
import com.transportapi.services.impl.geocoding.GeocodingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transportapi.documentation.ApiDocumentationRoute;
import com.transportapi.model.entity.Route;
import com.transportapi.services.RouteInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/route/")
@Tag(name = "Route", description = "Controlador registro de ubicación del usuario")
public class RouteController {
    @Autowired
    private RouteInterface routeService;

    @Autowired
    private  GeocodingImpl geocodingService;

    @Operation(summary = "Registra  el origen y el destino del  usuario",description = "Registra  el origen y el destino del  usuario")
    @ApiDocumentationRoute.RouteApiResponses
    @PostMapping("save")
    public ResponseEntity<Route> saveRoute(Principal principal, @RequestBody Route route) {
        Route savedRoute = routeService.saveRoute(principal, route);

        // Logs para verificar los valores formateados
        System.out.println("Distancia formateada: " + savedRoute.getFormattedDistance());
        System.out.println("Duración formateada: " + savedRoute.getFormattedDuration());
        System.out.println("Costo formateado: " + savedRoute.getFormattedCost());

        return ResponseEntity.ok(savedRoute);
    }

    @Operation(summary = "Obtiene todos los viajes realizados por el usuario",description = "obtiene todos los viajes realizados por el usuario")
    @ApiDocumentationRoute.RouteApiResponses
    @GetMapping("routes")
    public ResponseEntity<List<Route>> getAllRoute() {
        List<Route> routes = routeService.getAllRoute();
        if (routes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(routes);
    }

    @Operation(summary = "Obtiene el origen y el destino del usuario por ID",description = "Obtiene el origen y el destino del usuario por ID")
    @ApiDocumentationRoute.RouteApiResponses
    @GetMapping("get/{id}")
    public ResponseEntity<Route> findLocationById(@PathVariable Long id){
        Route location = routeService.findRouteById(id);
        return ResponseEntity.status(200).body(location);
    }

    @Operation(summary = "Actualiza el origen y el destino del usuario",description = "Actualiza el origen y el destino del usuario")
    @ApiDocumentationRoute.RouteApiResponses
    @PutMapping("update/{id}")
    public ResponseEntity<Route> update(@PathVariable Long id, @RequestBody Route location) {
        try {
            Route updatedLocation = routeService.updateRoute(id, location);
            return ResponseEntity.ok(updatedLocation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Elimina un origen y un destino registrado en la base de datos",description = "Elimina un origen y un destino registrado en la base de datos")
    @ApiDocumentationRoute.RouteApiResponses
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        try {
            routeService.deleteRoute(id);
            return ResponseEntity.ok().body("Information deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting information");
        }
    }
}



