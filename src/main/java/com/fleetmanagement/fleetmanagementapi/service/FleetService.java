package com.fleetmanagement.fleetmanagementapi.service;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import com.fleetmanagement.fleetmanagementapi.repository.CarRepository;
import com.fleetmanagement.fleetmanagementapi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FleetService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    public void assignDriverToCar(Long driverId, Long carId) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        Optional<Car> car = carRepository.findById(carId);

        if (driver.isEmpty() || car.isEmpty()) {
            throw new IllegalArgumentException("Driver or Car not found");
        }
        // Additional logic can be added here if needed
    }
}

