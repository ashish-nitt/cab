package com.mycomp.cab.repo;

import com.mycomp.cab.model.trip.TripEndRequest;
import com.mycomp.cab.model.trip.TripRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripEndRequestRepository extends CrudRepository<TripEndRequest, Long> {
}
