package com.mycomp.cab.service;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.trip.Trip;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.model.trip.Tripstatus;
import com.mycomp.cab.repo.TripRepository;
import com.mycomp.cab.repo.TripRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class TripService {
    @Autowired
    CabService cabService;
    @Autowired
    CityService cityService;
    @Autowired
    RequestService requestService;
    @Autowired
    TripRepository tripRepository;

    @Transactional
    public void handeTripRequest(TripRequest newTripRequest) {
        try {
            Trip trip = new Trip();
            trip.setRequestTime(new Date());
            trip.setCity(cityService.findCity(newTripRequest.getCityId()));
            trip.setTripStatus(Tripstatus.NEW_REQUEST);
            trip.setTripRequestId(newTripRequest.getId());
            tripRepository.save(trip);
            trip = tryAssignCab(trip);
            tripRepository.save(trip);
            if (trip.getTripStatus().equals(Tripstatus.CAB_ASSIGNED)) {
                requestService.updateRequestStatus(newTripRequest, RequestStatus.COMPLETED);
            }
        } catch (Exception ex) {
            requestService.updateRequestStatus(newTripRequest, RequestStatus.FAILED);
        }
    }

    @Transactional
    public Trip tryAssignCab(Trip trip) {
        tripRepository.save(trip);
        return trip;
    }
}
