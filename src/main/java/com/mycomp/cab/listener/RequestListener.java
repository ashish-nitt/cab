package com.mycomp.cab.listener;

import com.mycomp.cab.model.cab.CabRegisterRequest;
import com.mycomp.cab.model.cab.CabUpdateRequest;
import com.mycomp.cab.model.city.CityOnboardRequest;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.service.CabService;
import com.mycomp.cab.service.CityService;
import com.mycomp.cab.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class RequestListener {
    @Autowired
    CabService cabService;
    @Autowired
    CityService cityService;

    @Autowired
    TripService tripService;

    @JmsListener(destination = "CityOnboardRequest")
    public void onCityOnboardRequest(CityOnboardRequest request) {
        System.out.println("RequestListener.onCityOnboardRequest");
        cityService.onboardCity(request);
    }

    @JmsListener(destination = "CabRegisterRequest")
    public void onCabRegisterRequest(CabRegisterRequest request) {
        System.out.println("RequestListener.onCabRegisterRequest");
        cabService.registerCab(request);
    }

    @JmsListener(destination = "CabUpdateRequest")
    public void onCabUpdateRequest(CabUpdateRequest request) {
        System.out.println("RequestListener.onCabUpdateRequest");
        cabService.updateCab(request);
    }

    @JmsListener(destination = "TripRequest")
    public void onTripRequest(TripRequest request) {
        System.out.println("RequestListener.onTripRequest");
        tripService.handeTripRequest(request);
    }
}
