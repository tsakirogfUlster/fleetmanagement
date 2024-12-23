package com.fleetmanagement.fleetmanagementapi.controller;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import com.fleetmanagement.fleetmanagementapi.service.FleetService;
import com.fleetmanagement.fleetmanagementapi.repository.CarRepository;
import com.fleetmanagement.fleetmanagementapi.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@WebMvcTest(FleetController.class)
class FleetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FleetService fleetService;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private CarRepository carRepository;

    @Test
    void getAllDrivers_ShouldReturnListOfDrivers() throws Exception {
        // Arrange
        List<Driver> drivers = List.of(new Driver(1L, "John Doe", 0));
        when(driverRepository.findAll()).thenReturn(drivers);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/fleet/drivers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].penaltyPoints").value(0));

        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void addDriver_ShouldSaveDriver() throws Exception {
        // Arrange
        Driver driver = new Driver(null, "Jane Doe", 0);
        when(driverRepository.save(Mockito.any(Driver.class))).thenReturn(driver);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/fleet/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"penaltyPoints\": 0}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(driverRepository, times(1)).save(Mockito.any(Driver.class));
    }

    @Test
    void getAllCars_ShouldReturnListOfCars() throws Exception {
        // Arrange
        List<Car> cars = List.of(new Car(1L, "Sedan", "ABC123"));
        when(carRepository.findAll()).thenReturn(cars);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/fleet/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].licensePlate").value("ABC123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("Sedan"));

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void addCar_ShouldSaveCar() throws Exception {
        // Arrange
        Car car = new Car(null, "DEF456", "SUV");
        when(carRepository.save(Mockito.any(Car.class))).thenReturn(car);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/fleet/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\": \"DEF456\", \"type\": \"SUV\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(carRepository, times(1)).save(Mockito.any(Car.class));
    }

    @Test
    void assignDriverToCar_ShouldInvokeService() throws Exception {
        // Arrange
        doNothing().when(fleetService).assignDriverToCar(1L, 1L);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/fleet/assign")
                        .param("driverId", "1")
                        .param("carId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(fleetService, times(1)).assignDriverToCar(1L, 1L);
    }
}

