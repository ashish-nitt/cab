package com.mycomp.cab.service;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.trip.Trip;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.model.trip.Tripstatus;
import com.mycomp.cab.repo.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        System.out.println("TripService.handeTripRequest");
        try {
            Trip trip = new Trip();
            trip.setRequestTime(new Date());
            trip.setCity(cityService.findCity(newTripRequest.getCityId()));
            trip.setTripStatus(Tripstatus.NEW_REQUEST);
            trip.setRequestId(newTripRequest.getId());
            System.out.println("Trip " + trip.getId() + " created for trip request " + newTripRequest.getId());
            tripRepository.save(trip);
            trip = tryAssignCab(newTripRequest, trip);
            if (trip.getTripStatus().equals(Tripstatus.CAB_ASSIGNED)) {
                tripRepository.save(trip);
                requestService.updateRequestStatus(newTripRequest, RequestStatus.COMPLETED);
            }
        } catch (Exception ex) {
            requestService.updateRequestStatus(newTripRequest, RequestStatus.FAILED);
        }
    }

    public Trip tryAssignCab(TripRequest tripRequest, Trip trip) {
        System.out.println("TripService.tryAssignCab");
        try {
            Cab cab = cabService.findCabForTrip(tripRequest);
            if (cab != null) {
                trip.setCabAssigned(cab);
                System.out.println("Cab " + cab.getId() + " assigned for trip request " + tripRequest.getId());
                cabService.bookCab(cab.getId(), tripRequest.getId());
                trip.setTripStatus(Tripstatus.CAB_ASSIGNED);
            }
        } catch (Exception ex) {
            requestService.updateRequestStatus(tripRequest, RequestStatus.FAILED);
        }
        return trip;
    }

    public Trip findTripByRequestId(Long requestId) {
        return tripRepository.findByRequestId(requestId).orElseThrow(() -> new NoSuchElementException("RequestId not found"));
    }

    public Trip findTrip(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(() -> new NoSuchElementException("Invalid trip id"));
    }

    public List<Trip> findAllTrips() {
        return StreamSupport.stream(tripRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
