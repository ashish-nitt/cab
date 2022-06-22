package com.mycomp.cab.repo;

import com.mycomp.cab.model.trip.TripHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripHistoryRepository extends CrudRepository<TripHistory, Long> {
}
