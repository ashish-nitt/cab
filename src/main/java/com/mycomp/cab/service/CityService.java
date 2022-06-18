package com.mycomp.cab.service;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.city.CityOnboardRequest;
import com.mycomp.cab.repo.CityOnboardRequestRepository;
import com.mycomp.cab.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityOnboardRequestRepository cityOnboardRequestRepository;

    @Transactional
    public void onboardCity(CityOnboardRequest request) {
        try {
            City city = new City();
            city.setName(request.getCityName());
            city.setRequestId(request.getId());
            cityRepository.save(city);
            request.setStatus(RequestStatus.COMPLETED);
            cityOnboardRequestRepository.save(request);
        } catch (Exception e) {
            request.setStatus(RequestStatus.FAILED);
            cityOnboardRequestRepository.save(request);
        }
    }

    @Cacheable
    public City findCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new NoSuchElementException("Invalid city id"));
    }

    @Cacheable
    public City findCity(String cityName) {
        return cityRepository.findByName(cityName).orElseThrow(() -> new NoSuchElementException("Invalid city name"));
    }

    public List<City> findAllCities() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(), false).collect(Collectors.toList()); }

    public City findCityByRequestId(Long requestId) {
        System.out.println("CityService.findCityByRequestId");
        System.out.println("requestId = " + requestId);
        return cityRepository.findByRequestId(requestId).orElseThrow(() -> new NoSuchElementException("Invalid request id"));
    }
}
