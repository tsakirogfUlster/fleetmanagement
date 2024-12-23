package com.fleetmanagement.fleetmanagementapi;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FleetManagementApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddAndRetrieveDrivers() {
        // Arrange
        String url = "http://localhost:" + port + "/fleet/drivers";
        Driver driver = new Driver(null, "John Doe", 0);

        // Act - Add a Driver
        ResponseEntity<Void> postResponse = restTemplate.postForEntity(url, driver, Void.class);
        assertEquals(200, postResponse.getStatusCodeValue());

        // Act - Retrieve Drivers
        ResponseEntity<Driver[]> getResponse = restTemplate.getForEntity(url, Driver[].class);
        assertEquals(200, getResponse.getStatusCodeValue());
        Driver[] drivers = getResponse.getBody();

        // Assert
        assertNotNull(drivers);
        assertEquals(1, drivers.length);
        assertEquals("John Doe", drivers[0].getName());
    }

    @Test
    void testAddAndRetrieveCars() {
        // Arrange
        String url = "http://localhost:" + port + "/fleet/cars";
        Car car = new Car(null, "Sedan", "ABC123");

        // Act - Add a Car
        ResponseEntity<Void> postResponse = restTemplate.postForEntity(url, car, Void.class);
        assertEquals(200, postResponse.getStatusCodeValue());

        // Act - Retrieve Cars
        ResponseEntity<Car[]> getResponse = restTemplate.getForEntity(url, Car[].class);
        assertEquals(200, getResponse.getStatusCodeValue());
        Car[] cars = getResponse.getBody();

        // Assert
        assertNotNull(cars);
        assertEquals(1, cars.length);
        assertEquals("ABC123", cars[0].getLicensePlate());
    }

    @Test
    void testAssignDriverToCar() {
        // Arrange
        String driverUrl = "http://localhost:" + port + "/fleet/drivers";
        String carUrl = "http://localhost:" + port + "/fleet/cars";
        String assignUrl = "http://localhost:" + port + "/fleet/assign?driverId={driverId}&carId={carId}";

        Driver driver = new Driver(null, "Jane Doe", 0);
        Car car = new Car(null, "SUV", "DEF456");

        // Add Driver and Car
        restTemplate.postForEntity(driverUrl, driver, Void.class);
        restTemplate.postForEntity(carUrl, car, Void.class);

        // Act - Retrieve Driver and Car IDs
        Driver[] drivers = restTemplate.getForEntity(driverUrl, Driver[].class).getBody();
        Car[] cars = restTemplate.getForEntity(carUrl, Car[].class).getBody();

        // Assign Driver to Car
        restTemplate.postForEntity(assignUrl, null, Void.class, drivers[0].getId(), cars[0].getId());

        // Assert
        Car[] updatedCars = restTemplate.getForEntity(carUrl, Car[].class).getBody();
        assertNotNull(updatedCars[0].getDriver());
        assertEquals(drivers[0].getId(), updatedCars[0].getDriver().getId());
    }
}

