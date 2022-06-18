package com.mycomp.cab.repo;

import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.cab.CabState;
import com.mycomp.cab.model.city.City;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabRepository extends PagingAndSortingRepository<Cab, Long> {
    Optional<Cab> findFirstByCityAndState(City city, CabState state, Sort sort);
    Optional<Cab> findById(Long id);
    Optional<Cab> findByCabNumber(String cabNumber);
}