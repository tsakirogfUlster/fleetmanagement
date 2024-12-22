package com.fleetmanagement.fleetmanagementapi.repository;

import com.fleetmanagement.fleetmanagementapi.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {}
