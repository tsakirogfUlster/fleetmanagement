package com.fleetmanagement.heartbeater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HeartbeaterApplication {
	public static void main(String[] args) {
		SpringApplication.run(HeartbeaterApplication.class, args);
	}
}
