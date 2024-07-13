package com.transportapi.model.dao;

import org.springframework.data.repository.CrudRepository;
import com.transportapi.model.entity.Reservation;

public interface ReservationDao extends CrudRepository<Reservation, Long>{
    Long findRouteIdByReservationId(Long tripId);
}
