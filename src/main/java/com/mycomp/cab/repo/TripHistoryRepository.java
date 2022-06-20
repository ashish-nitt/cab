package com.mycomp.cab.repo;

import com.mycomp.cab.model.trip.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripHistoryRepository extends CrudRepository<Trip, Long> {
}
