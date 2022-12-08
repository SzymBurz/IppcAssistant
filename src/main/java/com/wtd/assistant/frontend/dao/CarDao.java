package com.wtd.assistant.frontend.dao;


import com.wtd.assistant.frontend.domain.Car;
import com.wtd.assistant.frontend.domain.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarDao extends CrudRepository<Car, Integer> {
    Optional<Car> findByTrips_Audits_TripId(Trip tripId);


}
