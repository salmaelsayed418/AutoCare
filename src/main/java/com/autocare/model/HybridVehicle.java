package com.autocare.model;

public class HybridVehicle extends Vehicule {

    private double batteryCapacity;
    private String fuelType;

    public HybridVehicle(String id, String brand, String model,
                         int year, String registrationNumber,
                         int mileage, double batteryCapacity,
                         String fuelType) {

        super(id, brand, model, year, registrationNumber, mileage);

        if (batteryCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Battery capacity must be positive"
            );
        }

        if (fuelType == null || fuelType.isBlank()) {
            throw new IllegalArgumentException(
                    "Fuel type cannot be empty"
            );
        }

        this.batteryCapacity = batteryCapacity;
        this.fuelType = fuelType;
    }

    @Override
    public double calculateDiagnosticFee() {
        return 75.0;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public String getFuelType() {
        return fuelType;
    }
}