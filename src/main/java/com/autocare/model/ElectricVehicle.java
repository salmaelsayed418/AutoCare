package com.autocare.model;

public class ElectricVehicle extends Vehicule {

    private double batteryCapacity;

    public ElectricVehicle(String id, String brand, String model,
                           int year, String registrationNumber,
                           int mileage, double batteryCapacity) {

        super(id, brand, model, year, registrationNumber, mileage);

        if (batteryCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Battery capacity must be positive"
            );
        }

        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public double calculateDiagnosticFee() {
        return 65.0; // signifie que le diagnostic d'un véhicule électrique coûte 65 €
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }
    // batteryCapacity est private, donc les autres classes ne peuvent pas y accéder directement
}