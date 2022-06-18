package com.mycomp.cab.controller;


import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.service.CabService;
import com.mycomp.cab.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
