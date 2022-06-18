package com.mycomp.cab.repo;

import com.mycomp.cab.model.cab.CabUpdateRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabUpdateRequestRepository extends CrudRepository<CabUpdateRequest, Long> {
    Optional<CabUpdateRequest> findById(Long id);
}