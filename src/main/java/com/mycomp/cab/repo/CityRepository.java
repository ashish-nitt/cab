package com.mycomp.cab.repo;

import com.mycomp.cab.model.city.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findByRequestId(Long id);
    Optional<City> findByName(String city);
}
