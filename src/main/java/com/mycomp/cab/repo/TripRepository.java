package com.mycomp.cab.repo;

import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.trip.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    Optional<Trip> findByRequestId(Long id);
}
