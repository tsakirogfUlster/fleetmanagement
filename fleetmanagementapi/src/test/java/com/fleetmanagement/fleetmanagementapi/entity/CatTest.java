package com.fleetmanagement.fleetmanagementapi.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String licensePlate = "ABC123";
        String type = "Sedan";

        // Act
        Car car = new Car(id, type, licensePlate);

        // Assert
        assertEquals(id, car.getId());
        assertEquals(licensePlate, car.getLicensePlate());
        assertEquals(type, car.getType());
        assertNull(car.getDriver(), "Driver should initially be null");
    }

    @Test
    void testSetDriver() {
        // Arrange
        Car car = new Car();
        Driver driver = new Driver(1L, "John Doe", 0);

        // Act
        car.setDriver(driver);

        // Assert
        assertNotNull(car.getDriver());
        assertEquals(driver, car.getDriver());
        assertEquals("John Doe", car.getDriver().getName());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Car car = new Car();

        // Assert
        assertNull(car.getId());
        assertNull(car.getLicensePlate());
        assertNull(car.getType());
        assertNull(car.getDriver());
    }
}

