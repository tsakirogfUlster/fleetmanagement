package com.fleetmanagement.heartbeater.service;

import com.fleetmanagement.heartbeater.model.Heartbeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class HeartbeatServiceTest {

    private HeartbeatService heartbeatService;

    @BeforeEach
    void setUp() {
        heartbeatService = new HeartbeatService();
    }

    //generateSimulatedHeartbeat() is private inside SendHartbeat() therefore we are using reflection
    @Test
    void generateSimulatedHeartbeat_ShouldProduceValidHeartbeat() throws Exception {
        // Access the private method using reflection
        Method method = HeartbeatService.class.getDeclaredMethod("generateSimulatedHeartbeat");
        method.setAccessible(true);

        // Invoke the private method
        Heartbeat heartbeat = (Heartbeat) method.invoke(heartbeatService);

        // Assert the generated heartbeat values
        assertNotNull(heartbeat);
        assertNotNull(heartbeat.getcarID());
        assertNotNull(heartbeat.getDriverId());
        assertNotNull(heartbeat.getGeoCoordinates());
        assertTrue(heartbeat.getSpeed() >= 0 && heartbeat.getSpeed() <= 120);
    }
}
