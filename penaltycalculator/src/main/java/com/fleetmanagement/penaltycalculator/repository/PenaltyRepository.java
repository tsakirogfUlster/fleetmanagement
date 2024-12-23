package com.fleetmanagement.penaltycalculator.repository;

import com.fleetmanagement.penaltycalculator.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    Penalty findByDriverId(String driverId);
}
