package com.mycomp.cab.repo;

import com.mycomp.cab.model.city.CityOnboardRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityOnboardRequestRepository extends CrudRepository<CityOnboardRequest, Long> {
    Optional<CityOnboardRequest> findById(Long id);
}
