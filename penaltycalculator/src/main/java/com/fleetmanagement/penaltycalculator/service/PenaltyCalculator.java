package com.fleetmanagement.penaltycalculator.service;

import java.util.HashMap;
import java.util.Map;

public class PenaltyCalculator {

    private final Map<String, Integer> penaltyMap;

    public PenaltyCalculator() {
        this.penaltyMap = new HashMap<>();
    }

    public int calculatePenalty(double speed) {
        if (speed > 80) {
            return 5; // 5 points for speed over 80 km/h
        } else if (speed > 60) {
            return 2; // 2 points for speed over 60 km/h
        }
        return 0; // No penalty for speed <= 60 km/h
    }

    public void applyPenalty(String driverId, int penaltyPoints) {
        penaltyMap.put(driverId, penaltyMap.getOrDefault(driverId, 0) + penaltyPoints);
        System.out.println("Penalty applied. Driver: " + driverId + ", Total Points: " + penaltyMap.get(driverId));
    }

    public int getTotalPenalty(String driverId) {
        return penaltyMap.getOrDefault(driverId, 0);
    }
}
