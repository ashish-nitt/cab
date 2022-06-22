package com.mycomp.cab;

import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.cab.CabState;
import com.mycomp.cab.model.trip.Trip;
import com.mycomp.cab.model.trip.Tripstatus;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

import static com.mycomp.cab.CabApplicationDemoUtils.*;

public class CabApplicationDemo {

    public static void demo() {
        Assert.isTrue(getCities().isEmpty(), ERROR_STR);
        Long requestId1 = postCityOnboardRequestResponseEntity("c1").getId();
        Long requestId2 = postCityOnboardRequestResponseEntity("c2").getId();
        CabApplicationDemoUtils.sleepForSomeTime();
        Long cityId1 = getCityByRequestId(requestId1);
        Long cityId2 = getCityByRequestId(requestId2);
        List<Long> cityIds = getCities();
        Assert.isTrue(cityIds.contains(cityId1), ERROR_STR);
        Assert.isTrue(cityIds.contains(cityId2), ERROR_STR);

        Assert.isTrue(getCabs().isEmpty(), ERROR_STR);
        Long requestId3 = postCabRegisterRequestResponseEntity(cityId1).getId();
        Long requestId4 = postCabRegisterRequestResponseEntity(cityId1).getId();
        Long requestId5 = postCabRegisterRequestResponseEntity(cityId1).getId();
        Long requestId6 = postCabRegisterRequestResponseEntity(cityId2).getId();
        Long requestId7 = postCabRegisterRequestResponseEntity(cityId2).getId();
        Long requestId8 = postCabRegisterRequestResponseEntity(cityId2).getId();
        CabApplicationDemoUtils.sleepForSomeTime();
        Long cabId1 = getCabByRequestId(requestId3);
        Long cabId2 = getCabByRequestId(requestId4);
        Long cabId3 = getCabByRequestId(requestId5);
        Long cabId4 = getCabByRequestId(requestId6);
        Long cabId5 = getCabByRequestId(requestId7);
        Long cabId6 = getCabByRequestId(requestId8);
        List<Long> cabIds = getCabs();
        Assert.isTrue(cabIds.contains(cabId1), ERROR_STR);
        Assert.isTrue(cabIds.contains(cabId2), ERROR_STR);
        Assert.isTrue(cabIds.contains(cabId3), ERROR_STR);
        Assert.isTrue(cabIds.contains(cabId4), ERROR_STR);
        Assert.isTrue(cabIds.contains(cabId5), ERROR_STR);
        Assert.isTrue(cabIds.contains(cabId6), ERROR_STR);

        Assert.isTrue(getTrips().isEmpty(), ERROR_STR);
        Long requestId9 = postTripRequestResponseEntity(cityId1).getId();
        Long requestId10 = postTripRequestResponseEntity(cityId1).getId();
        CabApplicationDemoUtils.sleepForSomeTime();
        Long tripId1 = getTripByRequestId(requestId9);
        Long tripId2 = getTripByRequestId(requestId10);
        Trip trip1 = getTrip(tripId1);
        Trip trip2 = getTrip(tripId2);
        Assert.isTrue(!Objects.equals(trip1.getCabAssigned().getId(), trip2.getCabAssigned().getId()), ERROR_STR);
        Assert.isTrue(trip1.getTripStatus().equals(Tripstatus.CAB_ASSIGNED), ERROR_STR);
        Assert.isTrue(trip2.getTripStatus().equals(Tripstatus.CAB_ASSIGNED), ERROR_STR);
        Cab cabAssigned1 = getCab(trip1.getCabAssigned().getId());
        Cab cabAssigned2 = getCab(trip1.getCabAssigned().getId());
        Assert.isTrue(cabAssigned1.getState().equals(CabState.ON_TRIP), ERROR_STR);
        Assert.isTrue(cabAssigned2.getState().equals(CabState.ON_TRIP), ERROR_STR);
        List<Long> tripIds = getTrips();
        Assert.isTrue(tripIds.contains(tripId1), ERROR_STR);
        Assert.isTrue(tripIds.contains(tripId2), ERROR_STR);

        Long reqEnd1 = postTripEndRequestResponseEntity(tripId1);
        Long reqEnd2 = postTripEndRequestResponseEntity(tripId2);
        CabApplicationDemoUtils.sleepForSomeTime();
        Trip trip11 = getTrip(tripId1);
        Trip trip22 = getTrip(tripId2);
        Assert.isTrue(trip11.getTripStatus().equals(Tripstatus.COMPLETED), ERROR_STR);
        Assert.isTrue(trip22.getTripStatus().equals(Tripstatus.COMPLETED), ERROR_STR);
    }


}
