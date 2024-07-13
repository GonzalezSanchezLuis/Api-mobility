package com.transportapi.services.impl;

import java.security.Principal;
import java.util.List;
import com.transportapi.services.impl.geocoding.GeocodingImpl;
import com.transportapi.util.RouteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.transportapi.model.dao.RouteDao;
import com.transportapi.model.dao.UserDao;
import com.transportapi.model.entity.Route;
import com.transportapi.model.entity.User;
import com.transportapi.services.RouteInterface;

@Service
public class RouteImpl implements RouteInterface {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GeocodingImpl geocodingImpl;

    private static final double EARTH_RADIUS = 6371;
    private static final double COST_PER_KILOMETER = 2000;
    private static final double EXCHANGE_RATE_USD_TO_COP = 3500;

    @Override
    public Route saveRoute(Principal principal, Route route) {
        String username = principal.getName();
        User user = userDao.findUserByEmail(username);

        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el correo electrónico: " + username);
        }

        route.setUser(user);

        double[] originCoordinates = geocodingImpl.getCoordinates(route.getOriginName());
        double[] destinationCoordinates = geocodingImpl.getCoordinates(route.getDestinationName());

        if (originCoordinates == null || destinationCoordinates == null) {
            throw new IllegalArgumentException("No se pudieron obtener las coordenadas para las direcciones proporcionadas.");
        }

        route.setOriginLatitude(originCoordinates[0]);
        route.setOriginLongitude(originCoordinates[1]);
        route.setDestinationLatitude(destinationCoordinates[0]);
        route.setDestinationLongitude(destinationCoordinates[1]);

        double distance = calculateDistance(originCoordinates[0], originCoordinates[1], destinationCoordinates[0], destinationCoordinates[1]);
        double cost = calculateCost(distance);
        int estimatedDuration = calculateEstimatedDuration(distance);

        // Convertir el costo a pesos colombianos
        double costInColombianPesos = convertCostToColombianPesos(cost);
        route.setCost(costInColombianPesos);

        // Formatear los valores antes de guardarlos
        route.setDistance(distance);
        route.setEstimatedDuration(estimatedDuration);
        route.setFormattedDistance(RouteUtils.formatDistance(route.getDistance()));
        route.setFormattedDuration(RouteUtils.formatDuration(route.getEstimatedDuration()));
        route.setFormattedCost(RouteUtils.formatCost(route.getCost()));

        // Guardar la ubicación en la base de datos
        route = routeDao.save(route);

        return route;
    }

    @Override
    public List<Route> getAllRoute() {
        List<Route> routes = (List<Route>) routeDao.findAll();
        routes.forEach(route -> {
            route.setFormattedDistance(RouteUtils.formatDistance(route.getDistance()));
            route.setFormattedDuration(RouteUtils.formatDuration(route.getEstimatedDuration()));
            route.setFormattedCost(RouteUtils.formatCost(route.getCost()));
        });
        return routes;
    }


    @Override
    public Route findRouteById(Long id) {
       Route findRouteById =  routeDao.findById(id).orElseThrow();
       if(findRouteById != null){
         return findRouteById;
       }else{
         throw new IllegalArgumentException("Invalid location ID: " + id);
       }
    }


    @Override
    public Route updateRoute(Long id, Route location) {
     Route existingLocation = routeDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid location ID: " + id));
     if (existingLocation != null) {
         existingLocation.setDestinationLongitude(location.getDestinationLongitude());
         existingLocation.setDestinationLatitude(location.getDestinationLatitude());
         existingLocation.setDestinationName(location.getDestinationName());
         existingLocation.setDistance(location.getDistance());
         existingLocation.setOriginLongitude(location.getOriginLongitude());
         existingLocation.setOriginLatitude(location.getOriginLatitude());
         existingLocation.setOriginName(location.getOriginName());
         existingLocation.setEstimatedDuration(location.getEstimatedDuration());
     }
    
    return routeDao.save(existingLocation);
    }

    public void deleteRoute(Long id) {
        try {
            routeDao.deleteById(id);
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


    
}
