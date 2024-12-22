package com.fleetmanagement.fleetmanagementapi.entity;

import jakarta.persistence.*;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int penaltyPoints;

    public Driver() {
    }

    public Driver(Long id, String name, int penaltyPoints) {
        this.id = id;
        this.name = name;
        this.penaltyPoints = penaltyPoints;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPenaltyPoints() {
        return penaltyPoints;
    }

}

