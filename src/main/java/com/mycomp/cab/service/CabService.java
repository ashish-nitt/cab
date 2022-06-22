package com.mycomp.cab.service;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.cab.CabRegisterRequest;
import com.mycomp.cab.model.cab.CabState;
import com.mycomp.cab.model.cab.CabUpdateRequest;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.trip.Trip;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.repo.CabRegisterRequestRepository;
import com.mycomp.cab.repo.CabRepository;
import com.mycomp.cab.repo.CabUpdateRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CabService {
    @Autowired
    CabRepository cabRepository;

    @Autowired
    CityService cityService;

    @Autowired
    CabRegisterRequestRepository cabRegisterRequestRepository;

    @Autowired
    CabUpdateRequestRepository cabUpdateRequestRepository;

    @Transactional
    public void registerCab(CabRegisterRequest request) {
        try {
            Cab cab = new Cab();
            City city = cityService.findCity(request.getCityId());
            if (city == null) {
                throw new NoSuchElementException("No city found");
            }
            cab.setCity(city);
            cab.setState(CabState.IDLE);
            cab.setCabNumber(request.getCabNumber());
            cab.setIdleStateStartTime(new Date());
            cab.setRegisterRequestId(request.getId());
            cabRepository.save(cab);
            request.setStatus(RequestStatus.COMPLETED);
            cabRegisterRequestRepository.save(request);
        } catch (Exception e) {
            request.setStatus(RequestStatus.FAILED);
            cabRegisterRequestRepository.save(request);
        }
    }

    @Transactional
    public void updateCab(CabUpdateRequest request) {
        try {
            Long cityId = request.getCityId();
            CabState cabState = request.getCabState();
            Cab cab = findCab(request.getCabId());
            if (Objects.nonNull(cityId))
                cab.setCity(cityService.findCity(cityId));
            if (Objects.nonNull(cabState))
                cab.setState(cabState);
            cab.setUpdateRequestId(request.getId());
            cabRepository.save(cab);
            request.setStatus(RequestStatus.COMPLETED);
            cabUpdateRequestRepository.save(request);
        } catch (Exception e) {
            request.setStatus(RequestStatus.FAILED);
            cabUpdateRequestRepository.save(request);
        }
    }

    public void bookCab(Long cabId, Long requestId) {
        Cab cab = findCab(cabId);
        if (!cab.getState().equals(CabState.ON_TRIP)) {
            cab.setState(CabState.ON_TRIP);
        } else {
            throw new IllegalStateException("Cab is already booked");
        }
        cab.setUpdateRequestId(requestId);
        cabRepository.save(cab);
    }

    public void freeCab(Long cabId, Long requestId) {
        Cab cab = findCab(cabId);
        if (cab.getState().equals(CabState.ON_TRIP)) {
            cab.setState(CabState.IDLE);
        } else {
            throw new IllegalStateException("Cab is not booked");
        }
        cab.setUpdateRequestId(requestId);
        cab.setIdleStateStartTime(new Date());
        cabRepository.save(cab);
    }

    public Cab findCabForTrip(TripRequest tripRequest) {
        return cabRepository.findFirstByCityAndState(cityService.findCity(tripRequest.getCityId()), CabState.IDLE,
                        Sort.by(Sort.Direction.ASC, "idleStateStartTime"))
                .orElseThrow(() -> new NoSuchElementException("Cab not found"));
    }

    public List<Cab> findAllCabs() {
        return StreamSupport.stream(cabRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Cab findCabByRequestId(Long requestId) {
        return cabRepository.findByRegisterRequestId(requestId).orElseThrow(() -> new NoSuchElementException("Request id not found"));
    }

    public Cab findCab(Long cabId) {
        return cabRepository.findById(cabId).orElseThrow(() -> new NoSuchElementException("Invalid cab id"));
    }

    public Cab findCab(String canbNumber) {
        return cabRepository.findByCabNumber(canbNumber).orElseThrow(() -> new NoSuchElementException("Invalid cab Number"));
    }
}
