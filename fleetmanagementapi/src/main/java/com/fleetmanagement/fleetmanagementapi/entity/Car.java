package com.fleetmanagement.fleetmanagementapi.entity;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private String type;

    @ManyToOne
    private Driver driver;

    public Car(Long id, String type, String licensePlate) {
        this.id = id;
        this.type = type;
        this.licensePlate = licensePlate;
    }

    public Car() {

    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getType() {
        return type;
    }

    public Driver getDriver() {
        return driver;
    }

    //This is assignDriver in basic analysis UML
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
