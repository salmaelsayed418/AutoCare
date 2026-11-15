package com.autocare.model;

public abstract class Vehicule {
    // Pourquoi abstract ? CarVehicle sera notre classe mère.
    // On ne veut pas créer directement un véhicule générique :
    // on veut créer un véhicule électrique, thermique ou hybride.

    private String id;
    private String brand;
    private String model;
    private int year;
    private String registrationNumber;
    private int mileage;


    public Vehicule(String id, String brand, String model,
                   int year, String registrationNumber, int mileage) {

        if (year <= 0) {
            throw new IllegalArgumentException("Invalid vehicle year");
        }

        if (mileage < 0) {
            throw new IllegalArgumentException("Mileage cannot be negative");
        }

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getMileage() {
        return mileage;
    }

    public void updateMileage(int newMileage) {

        if (newMileage < mileage) {
            throw new IllegalArgumentException(
                    "New mileage cannot be lower than current mileage"
            );
        }

        this.mileage = newMileage;
    }

    public abstract double calculateDiagnosticFee();
}
