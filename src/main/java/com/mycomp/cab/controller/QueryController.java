package com.mycomp.cab.controller;


import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.trip.Trip;
import com.mycomp.cab.model.trip.TripHistory;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.service.CabService;
import com.mycomp.cab.service.CityService;
import com.mycomp.cab.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class QueryController {
    @Autowired
    CabService cabService;
    @Autowired
    CityService cityService;
    @Autowired
    TripService tripService;

    @GetMapping(value = "/cities")
    ResponseEntity<List<Long>> findCities(@RequestParam(value = "requestId", required = false) Long requestId) {
        System.out.println("QueryController.findCities");
        System.out.println("requestId = " + requestId);
        if (requestId != null) {
            return ResponseEntity.ok(Arrays.asList(cityService.findCityByRequestId(requestId).getId()));
        } else {
            return ResponseEntity.ok(cityService.findAllCities().stream().map(City::getId).collect(Collectors.toList()));
        }
    }

    @GetMapping(value = "/city/{cityId}")
    ResponseEntity<City> findCity(@PathVariable Long cityId) {
        System.out.println("QueryController.findCity");
        System.out.println("cityId = " + cityId);
        return ResponseEntity.ok(cityService.findCity(cityId));
    }

    @GetMapping(value = "/cabs")
    ResponseEntity<List<Long>> findCabs(@RequestParam(value = "requestId", required = false) Long requestId) {
        System.out.println("QueryController.findCabs");
        System.out.println("requestId = " + requestId);
        if (requestId != null) {
            return ResponseEntity.ok(Arrays.asList(cabService.findCabByRequestId(requestId).getId()));
        } else {
            return ResponseEntity.ok(cabService.findAllCabs().stream().map(Cab::getId).collect(Collectors.toList()));
        }
    }

    @GetMapping(value = "/cab/{cabId}")
    ResponseEntity<Cab> findCab(@PathVariable Long cabId) {
        System.out.println("QueryController.findCab");
        System.out.println("cabId = " + cabId);
        return ResponseEntity.ok(cabService.findCab(cabId));
    }

    @GetMapping(value = "/trips")
    ResponseEntity<List<Long>> findTrips(@RequestParam(value = "requestId", required = false) Long requestId) {
        System.out.println("QueryController.findTrips");
        System.out.println("requestId = " + requestId);
        if (requestId != null) {
            return ResponseEntity.ok(Arrays.asList(tripService.findTripByRequestId(requestId).getId()));
        } else {
            return ResponseEntity.ok(tripService.findAllTrips().stream().map(Trip::getId).collect(Collectors.toList()));
        }
    }

    @GetMapping(value = "/triphistories")
    ResponseEntity<List<Long>> findTripHistories() {
        System.out.println("QueryController.findTrips");
        return ResponseEntity.ok(tripService.findAllTripHistories().stream().map(TripHistory::getId).collect(Collectors.toList()));
    }

    @GetMapping(value = "/trip/{tripId}")
    ResponseEntity<Trip> findTrip(@PathVariable Long tripId) {
        System.out.println("QueryController.findTrip");
        System.out.println("tripId = " + tripId);
        return ResponseEntity.ok(tripService.findTrip(tripId));
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            NoSuchElementException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
