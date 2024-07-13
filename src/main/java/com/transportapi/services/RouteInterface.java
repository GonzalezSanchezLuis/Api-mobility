package com.transportapi.services;

import java.security.Principal;
import java.util.List;

import com.transportapi.model.entity.Route;

public interface RouteInterface {
    Route saveRoute(Principal principal, Route route);
    List<Route> getAllRoute();
    Route findRouteById(Long id);
    Route updateRoute(Long id, Route location);
    void deleteRoute(Long id);
}
