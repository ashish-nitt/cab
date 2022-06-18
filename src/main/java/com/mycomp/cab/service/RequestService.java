package com.mycomp.cab.service;

import com.mycomp.cab.model.RequestStatus;
import com.mycomp.cab.model.trip.TripRequest;
import com.mycomp.cab.model.cab.CabRegisterRequest;
import com.mycomp.cab.model.cab.CabUpdateRequest;
import com.mycomp.cab.model.city.CityOnboardRequest;
import com.mycomp.cab.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class RequestService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("CabRegisterRequest")
    private Queue cabRegisterRequestQueue;

    @Autowired
    @Qualifier("CabUpdateRequest")
    public Queue cabUpdateRequestQueue;

    @Autowired
    @Qualifier("CityOnboardRequest")
    public Queue cityOnboardRequestQueue;

    @Autowired
    @Qualifier("TripRequest")
    public Queue tripRequestQueue;

    @Autowired
    private CabRegisterRequestRepository cabRegisterRequestRepository;

    @Autowired
    private CabUpdateRequestRepository cabUpdateRequestRepository;

    @Autowired
    private CityOnboardRequestRepository cityOnboardRequestRepository;

    @Autowired
    private TripRequestRepository tripRequestRepository;


    public CabRegisterRequest publishRequest(CabRegisterRequest request) {
        request = updateRequestStatus(request, RequestStatus.PENDING);
        jmsTemplate.convertAndSend(cabRegisterRequestQueue, request);
        return request;
    }

    public CabRegisterRequest updateRequestStatus(CabRegisterRequest request, RequestStatus status) {
        request.setStatus(status);
        return cabRegisterRequestRepository.save(request);
    }

    public CabUpdateRequest publishRequest(CabUpdateRequest request) {
        request = updateRequestStatus(request, RequestStatus.PENDING);
        jmsTemplate.convertAndSend(cabUpdateRequestQueue, request);
        return request;
    }

    public CabUpdateRequest updateRequestStatus(CabUpdateRequest request, RequestStatus status) {
        request.setStatus(status);
        return cabUpdateRequestRepository.save(request);
    }

    public CityOnboardRequest publishRequest(CityOnboardRequest request) {
        System.out.println("RequestService.publishRequest");
        request = updateRequestStatus(request, RequestStatus.PENDING);
        jmsTemplate.convertAndSend(cityOnboardRequestQueue, request);
        System.out.println("request = " + request.getId());
        return request;
    }

    public CityOnboardRequest updateRequestStatus(CityOnboardRequest request, RequestStatus status) {
        request.setStatus(status);
        return cityOnboardRequestRepository.save(request);
    }

    public TripRequest publishRequest(TripRequest request) {
        request = updateRequestStatus(request, RequestStatus.PENDING);
        jmsTemplate.convertAndSend(tripRequestQueue, request);
        return request;
    }

    public TripRequest updateRequestStatus(TripRequest request, RequestStatus status) {
        request.setStatus(status);
        return tripRequestRepository.save(request);
    }
}