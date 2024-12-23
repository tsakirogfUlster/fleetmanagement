package com.fleetmanagement.fleetmanagementapi.service;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import com.fleetmanagement.fleetmanagementapi.repository.CarRepository;
import com.fleetmanagement.fleetmanagementapi.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FleetServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private FleetService fleetService;

    public FleetServiceTest() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
    }

    @Test
    void assignDriverToCar_ShouldAssignDriver_WhenBothExist() {
        // Arrange
        Long driverId = 1L;
        Long carId = 2L;
        Driver driver = new Driver(driverId, "John Doe", 0);
        Car car = new Car(carId, "Sedan", "ABC123");

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        // Act
        fleetService.assignDriverToCar(driverId, carId);

        // Assert
        assertEquals(driver, car.getDriver(), "The car should have the assigned driver");
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void assignDriverToCar_ShouldThrowException_WhenDriverNotFound() {
        // Arrange
        Long driverId = 1L;
        Long carId = 2L;

        when(driverRepository.findById(driverId)).thenReturn(Optional.empty());
        when(carRepository.findById(carId)).thenReturn(Optional.of(new Car(carId, "Sedan", "ABC123")));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fleetService.assignDriverToCar(driverId, carId));
        assertEquals("Driver or Car not found", exception.getMessage());

        verify(carRepository, never()).save(any());
    }

    @Test
    void assignDriverToCar_ShouldThrowException_WhenCarNotFound() {
        // Arrange
        Long driverId = 1L;
        Long carId = 2L;

        when(driverRepository.findById(driverId)).thenReturn(Optional.of(new Driver(driverId, "John Doe", 0)));
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                fleetService.assignDriverToCar(driverId, carId));
        assertEquals("Driver or Car not found", exception.getMessage());

        verify(carRepository, never()).save(any());
    }
}
