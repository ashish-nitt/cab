package com.mycomp.cab.controller;


import com.mycomp.cab.model.cab.CabRegisterRequest;
import com.mycomp.cab.model.cab.CabUpdateRequest;
import com.mycomp.cab.model.city.CityOnboardRequest;
import com.mycomp.cab.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class RegistrationAndOnboardingController {
    @Autowired
    RequestService requestService;

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            NoSuchElementException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @PostMapping(value = "/cities", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CityOnboardRequest> onboardCity(@RequestBody CityOnboardRequest request) {
        return ResponseEntity.ok(requestService.publishRequest(request));
    }

    @PostMapping(value = "/cabs", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CabRegisterRequest> registerCab(@RequestBody CabRegisterRequest request) {
        return ResponseEntity.ok(requestService.publishRequest(request));
    }

    @PatchMapping(value = "/cab/{cabId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CabUpdateRequest> updateCab(@PathVariable Long cabId, @RequestBody CabUpdateRequest request) {
        return ResponseEntity.ok(requestService.publishRequest(request));
    }
}
