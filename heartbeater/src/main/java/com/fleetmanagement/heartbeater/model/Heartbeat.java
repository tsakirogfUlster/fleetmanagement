package com.fleetmanagement.heartbeater.model;

public class Heartbeat {
    private String carID;
    private String driverId;
    private String geoCoordinates;
    private double speed;

    // Getters and Setters
    public String getcarID() {
        return carID;
    }

    public void setcarID(String car_id) {
        this.carID = car_id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}