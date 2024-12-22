package com.fleetmanagement.fleetmanagementapi.controller;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import com.fleetmanagement.fleetmanagementapi.service.FleetService;
import com.fleetmanagement.fleetmanagementapi.repository.CarRepository;
import com.fleetmanagement.fleetmanagementapi.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fleet")
public class FleetController {

    @Autowired
    private FleetService fleetService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @PostMapping("/drivers")
    public void addDriver(@RequestBody Driver driver) {
        driverRepository.save(driver);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @PostMapping("/cars")
    public void addCar(@RequestBody Car car) {
        carRepository.save(car);
    }

    @PostMapping("/assign")
    public void assignDriverToCar(@RequestParam Long driverId, @RequestParam Long carId) {
        fleetService.assignDriverToCar(driverId, carId);
    }
}

