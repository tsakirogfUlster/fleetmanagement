package com.fleetmanagement.fleetmanagementapi.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @Test
    void testDriverConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String name = "John Doe";
        int penaltyPoints = 10;

        // Act
        Driver driver = new Driver(id, name, penaltyPoints);

        // Assert
        assertEquals(id, driver.getId());
        assertEquals(name, driver.getName());
        assertEquals(penaltyPoints, driver.getPenaltyPoints());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Driver driver = new Driver();

        // Assert
        assertNull(driver.getId());
        assertNull(driver.getName());
        assertEquals(0, driver.getPenaltyPoints(), "Penalty points should default to 0");
    }
}
