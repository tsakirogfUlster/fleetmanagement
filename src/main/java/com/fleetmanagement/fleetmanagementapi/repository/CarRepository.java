package com.fleetmanagement.fleetmanagementapi.repository;

import com.fleetmanagement.fleetmanagementapi.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {}

