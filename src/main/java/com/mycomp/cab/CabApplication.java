package com.mycomp.cab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import static com.mycomp.cab.CabApplicationDemo.demo;

@SpringBootApplication
@EnableJms
public class CabApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabApplication.class, args);
		demo();
	}
}
