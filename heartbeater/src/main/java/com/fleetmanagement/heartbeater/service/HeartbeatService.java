package com.fleetmanagement.heartbeater.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fleetmanagement.heartbeater.model.Heartbeat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Random;

@Service
public class HeartbeatService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Autowired
//    private RestTemplate restTemplate;

    private final RestTemplate restTemplate;

    public HeartbeatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String KAFKA_TOPIC = "heartbeat-topic";

    // Starting coordinates for simulation in Belfast, UK
    private double latitude = 54.5973;  // Belfast latitude
    private double longitude = -5.9301; // Belfast longitude

    private double speed = 50.0; // Initial speed in km/h
    private double direction = random.nextDouble() * 360; // Direction in degrees

    public void sendHeartbeat() {
        Heartbeat heartbeat = generateSimulatedHeartbeat();
        try {
            String message = objectMapper.writeValueAsString(heartbeat);
            kafkaTemplate.send(KAFKA_TOPIC, message);
            System.out.println("Sent: " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Heartbeat generateSimulatedHeartbeat() {

        //First we need to check for the first car in the api, with an assigned driver
        String[] carAndDriverIds = fetchCarAndDriverIds();

        if (carAndDriverIds == null) {
            throw new IllegalStateException("No car with an assigned driver found.");
        }

        // Update speed with some random fluctuation (simulate acceleration/deceleration)
        speed += (random.nextDouble() - 0.5) * 10; // Random change of ±5 km/h
        speed = Math.max(0, Math.min(speed, 120)); // Clamp speed between 0 and 120 km/h

        // Convert speed from km/h to m/s for calculations
        double speedInMetersPerSecond = speed * 1000 / 3600;

        // Calculate new coordinates based on speed, direction, and a 5-second interval
        double distance = speedInMetersPerSecond * 5; // Distance covered in 5 seconds
        double distanceInDegrees = distance / 111000; // Approximation: 1 degree ≈ 111 km

        latitude += distanceInDegrees * Math.cos(Math.toRadians(direction));
        longitude += distanceInDegrees * Math.sin(Math.toRadians(direction));

        // Randomly change direction slightly
        direction += (random.nextDouble() - 0.5) * 10; // Random change of ±5 degrees
        direction = direction % 360; // Ensure direction stays within 0-360 degrees

        // Create a new heartbeat object
        Heartbeat heartbeat = new Heartbeat();
        heartbeat.setcarID(carAndDriverIds[0]);
        heartbeat.setDriverId(carAndDriverIds[1]);
        heartbeat.setGeoCoordinates(latitude + "," + longitude);
        heartbeat.setSpeed(speed);

        return heartbeat;
    }

    private String[] fetchCarAndDriverIds() {
        // this needs to be updated using config to identify runtime platform and act accodrinally
        final String FLEET_CARS_API = "http://fleetmanagementapi:8081/fleet/cars";

        try {
            // Make a GET request to fetch the cars
            String jsonResponse = restTemplate.getForObject(FLEET_CARS_API, String.class);
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON response
            JsonNode carsNode = objectMapper.readTree(jsonResponse);

            for (JsonNode carNode : carsNode) {
                JsonNode driverNode = carNode.get("driver");

                if (driverNode != null) {
                    String carId = carNode.get("id").asText(); // Use "id" for the car ID
                    String driverId = driverNode.get("id").asText(); // Use "id" for the driver ID
                    return new String[]{carId, driverId};
                }
            }

            System.out.println("No cars with assigned drivers available.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
