package com.fleetmanagement.heartbeater.service;

import com.fleetmanagement.heartbeater.model.Heartbeat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HeartbeatService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

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
        heartbeat.setcarID("1");
        heartbeat.setDriverId("1");
        heartbeat.setGeoCoordinates(latitude + "," + longitude);
        heartbeat.setSpeed(speed);

        return heartbeat;
    }
}
