package com.autocare.model;

public class CombustionVehicle extends Vehicule {

    private String fuelType;

    public CombustionVehicle(String id, String brand, String model,
                             int year, String registrationNumber,
                             int mileage, String fuelType) {

        super(id, brand, model, year, registrationNumber, mileage);

        if (fuelType == null || fuelType.isBlank()) {
            throw new IllegalArgumentException("Fuel type cannot be empty");
        }

        this.fuelType = fuelType;
    }

    @Override
    public double calculateDiagnosticFee() {
        return 45.0;
    }

    public String getFuelType() {
        return fuelType;
    }
}