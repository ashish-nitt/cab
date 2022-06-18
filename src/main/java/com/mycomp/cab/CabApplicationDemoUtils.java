package com.mycomp.cab;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.cab.CabRegisterRequest;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.city.CityOnboardRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

public class CabApplicationDemoUtils {
    private static final String BASE_URL = "http://localhost:8080/cabservice";

    static RestTemplate restTemplate = new RestTemplate();
    static final String ERROR_STR = "RestCallfailed";
    static void sleepForSomeTime() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static CityOnboardRequest postCityOnboardRequestResponseEntity(String cityName) {
        ResponseEntity<CityOnboardRequest> response = restTemplate.postForEntity(BASE_URL + "/cities", CityOnboardRequest.builder().cityName(cityName).build(), CityOnboardRequest.class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        Assert.isTrue(response.getBody().getCityName().equals(cityName), ERROR_STR);
        Assert.isTrue(response.getBody().getStatus().equals(RequestStatus.PENDING), ERROR_STR);
        return response.getBody();
    }

    static CabRegisterRequest postCabRegisterRequestResponseEntity(Long cityId) {
        ResponseEntity<CabRegisterRequest> response = restTemplate.postForEntity(BASE_URL + "/cabs", CabRegisterRequest.builder().cityId(cityId).build(), CabRegisterRequest.class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        Assert.isTrue(response.getBody().getCityId().equals(cityId), ERROR_STR);
        Assert.isTrue(response.getBody().getStatus().equals(RequestStatus.PENDING), ERROR_STR);
        return response.getBody();
    }

    static City getCity(long id) {
        ResponseEntity<City> response = restTemplate.getForEntity(BASE_URL + "/city/"+id, City.class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        Assert.isTrue(response.getBody().getName().equals("c1"), ERROR_STR);
        Assert.isTrue(response.getBody().equals(id), ERROR_STR);
        return response.getBody();
    }

    static Long getCityByRequestId(Long requestId) {
        System.out.println("CabApplication.getCityByRequestId requestId = " + requestId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "/cities")
                .queryParam("requestId", requestId);
        ResponseEntity<Long[]> response = restTemplate.getForEntity(builder.build().toUri(), Long[].class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        return response.getBody().length == 0 ? null : response.getBody()[0];
    }

    static Long getCabByRequestId(Long requestId) {
        System.out.println("CabApplication.getCabByRequestId requestId = " + requestId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "/cabs")
                .queryParam("requestId", requestId);
        ResponseEntity<Long[]> response = restTemplate.getForEntity(builder.build().toUri(), Long[].class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        return response.getBody().length == 0 ? null : response.getBody()[0];
    }

    static List<Long> getCities() {
        ResponseEntity<Long[]> response = restTemplate.getForEntity(BASE_URL + "/cities", Long[].class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        return Arrays.asList(response.getBody());
    }

    static List<Long> getCabs() {
        ResponseEntity<Long[]> response = restTemplate.getForEntity(BASE_URL + "/cabs", Long[].class);
        Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
        return Arrays.asList(response.getBody());
    }
}
