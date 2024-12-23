package com.fleetmanagement.penaltycalculator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "penalty")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_id", unique = true, nullable = false)
    private String driverId;

    @Column(name = "points", nullable = false)
    private int points;

    public Penalty() {
    }

    public Penalty(String driverId, int points) {
        this.driverId = driverId;
        this.points = points;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}
