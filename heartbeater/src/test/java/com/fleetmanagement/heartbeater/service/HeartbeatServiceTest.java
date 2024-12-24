package com.fleetmanagement.heartbeater.service;

import com.fleetmanagement.heartbeater.model.Heartbeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HeartbeatServiceTest {

    private HeartbeatService heartbeatService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Mock the RestTemplate
        restTemplate = mock(RestTemplate.class);

        // Pass the mocked RestTemplate to the service
        heartbeatService = new HeartbeatService(restTemplate);
    }

    @Test
    void generateSimulatedHeartbeat_ShouldProduceValidHeartbeat() throws Exception {
        // Arrange: Mock the response from the Fleet Management API
        String mockResponse = "[{\"id\":1,\"licensePlate\":\"ABC123\",\"type\":\"Sedan\",\"driver\":{\"id\":2,\"name\":\"John Doe\",\"penaltyPoints\":0}}]";
        when(restTemplate.getForObject("http://localhost:8081/fleet/cars", String.class)).thenReturn(mockResponse);

        // Access the private method using reflection
        Method method = HeartbeatService.class.getDeclaredMethod("generateSimulatedHeartbeat");
        method.setAccessible(true);

        // Act: Invoke the private method
        Heartbeat heartbeat = (Heartbeat) method.invoke(heartbeatService);

        // Assert: Verify the generated heartbeat
        assertNotNull(heartbeat);
        assertEquals("1", heartbeat.getcarID());
        assertEquals("2", heartbeat.getDriverId());
        assertNotNull(heartbeat.getGeoCoordinates());
        assertTrue(heartbeat.getSpeed() >= 0 && heartbeat.getSpeed() <= 120);
    }
}
