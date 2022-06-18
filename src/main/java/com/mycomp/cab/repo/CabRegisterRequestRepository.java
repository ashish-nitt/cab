package com.mycomp.cab.repo;

import com.mycomp.cab.model.cab.CabRegisterRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabRegisterRequestRepository extends CrudRepository<CabRegisterRequest, Long> {
    Optional<CabRegisterRequest> findById(Long id);
}
