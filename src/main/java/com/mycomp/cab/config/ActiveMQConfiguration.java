package com.mycomp.cab.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;


@EnableJms
@Configuration
public  class ActiveMQConfiguration {
    @Bean("CabRegisterRequest")
    public Queue createQueue1() {
        return new ActiveMQQueue("CabRegisterRequest");
    }

    @Bean("CabUpdateRequest")
    public Queue createQueue2() {
        return new ActiveMQQueue("CabUpdateRequest");
    }

    @Bean("CityOnboardRequest")
    public Queue createQueue3() {
        return new ActiveMQQueue("CityOnboardRequest");
    }

    @Bean("TripRequest")
    public Queue createQueue4() {
        return new ActiveMQQueue("TripRequest");
    }

    @Bean("TripEndRequest")
    public Queue createQueue5() {
        return new ActiveMQQueue("TripEndRequest");
    }
}