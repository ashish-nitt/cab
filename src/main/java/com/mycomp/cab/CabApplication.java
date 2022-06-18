package com.mycomp.cab;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.city.City;
import com.mycomp.cab.model.city.CityOnboardRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJms
public class CabApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabApplication.class, args);
		demo();
	}


	private static final String BASE_URL = "http://localhost:8080/cabservice";

	private static RestTemplate restTemplate = new RestTemplate();
	private static final String ERROR_STR = "RestCallfailed";


	public static void demo() {

		Assert.isTrue(getCities().isEmpty(), ERROR_STR);

		Long requestId1 = postCityOnboardRequestResponseEntity("c1").getId();
		Long requestId2 = postCityOnboardRequestResponseEntity("c2").getId();
		sleepForSomeTime();
		Long cityId1 = getCityByRequestId(requestId1);
		Long cityId2 = getCityByRequestId(requestId2);
		List<Long> cityIds = getCities();
		Assert.isTrue(cityIds.contains(cityId1), ERROR_STR);
		Assert.isTrue(cityIds.contains(cityId2), ERROR_STR);
	}

	private static void sleepForSomeTime() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static CityOnboardRequest postCityOnboardRequestResponseEntity(String cityName) {
		ResponseEntity<CityOnboardRequest> response = restTemplate.postForEntity(BASE_URL + "/cities", CityOnboardRequest.builder().cityName(cityName).build(), CityOnboardRequest.class);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
		Assert.isTrue(response.getBody().getCityName().equals(cityName), ERROR_STR);
		Assert.isTrue(response.getBody().getStatus().equals(RequestStatus.PENDING), ERROR_STR);
		return response.getBody();
	}

	private static City getCity(long id) {
		ResponseEntity<City> response = restTemplate.getForEntity(BASE_URL + "/city/"+id, City.class);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
		Assert.isTrue(response.getBody().getName().equals("c1"), ERROR_STR);
		Assert.isTrue(response.getBody().getId().equals(id), ERROR_STR);
		return response.getBody();
	}

	private static Long getCityByRequestId(Long requestId) {
		System.out.println("CabApplication.getCityByRequestId requestId = " + requestId);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "/cities")
				.queryParam("requestId", requestId);
		ResponseEntity<Long[]> response = restTemplate.getForEntity(builder.build().toUri(), Long[].class);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
		return response.getBody().length == 0 ? null : response.getBody()[0];
	}

	private static List<Long> getCities() {
		ResponseEntity<Long[]> response = restTemplate.getForEntity(BASE_URL + "/cities", Long[].class);
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK), ERROR_STR);
		return Arrays.asList(response.getBody());
	}
}
