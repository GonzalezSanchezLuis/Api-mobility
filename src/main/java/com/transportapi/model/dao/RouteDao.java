package com.transportapi.model.dao;

import org.springframework.data.repository.CrudRepository;
import com.transportapi.model.entity.Route;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteDao extends CrudRepository<Route, Long>{
    
}
