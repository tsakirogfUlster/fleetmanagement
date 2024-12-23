package com.fleetmanagement.heartbeater.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeartbeatTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        Heartbeat heartbeat = new Heartbeat();

        String carID = "CAR123";
        String driverId = "DRIVER456";
        String geoCoordinates = "40.7128,-74.0060";
        double speed = 85.5;

        // Act
        heartbeat.setcarID(carID);
        heartbeat.setDriverId(driverId);
        heartbeat.setGeoCoordinates(geoCoordinates);
        heartbeat.setSpeed(speed);

        // Assert
        assertEquals(carID, heartbeat.getcarID(), "Car ID should match the value set");
        assertEquals(driverId, heartbeat.getDriverId(), "Driver ID should match the value set");
        assertEquals(geoCoordinates, heartbeat.getGeoCoordinates(), "Geo-coordinates should match the value set");
        assertEquals(speed, heartbeat.getSpeed(), "Speed should match the value set");
    }

    @Test
    void testDefaultValues() {
        // Arrange & Act
        Heartbeat heartbeat = new Heartbeat();

        // Assert
        assertNull(heartbeat.getcarID(), "Car ID should be null by default");
        assertNull(heartbeat.getDriverId(), "Driver ID should be null by default");
        assertNull(heartbeat.getGeoCoordinates(), "Geo-coordinates should be null by default");
        assertEquals(0.0, heartbeat.getSpeed(), "Speed should be 0.0 by default");
    }
}

