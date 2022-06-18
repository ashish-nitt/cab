package com.mycomp.cab;

import org.springframework.util.Assert;

import java.util.List;

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
    }


}
