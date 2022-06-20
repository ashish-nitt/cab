package com.mycomp.cab.repo;

import com.mycomp.cab.model.trip.TripRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRequestRepository extends CrudRepository<TripRequest, Long> {
}
