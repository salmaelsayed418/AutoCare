package com.autocare.service;

import com.autocare.model.Vehicule;
import com.autocare.repository.VehicleRepository;

import java.util.List;


public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService() {
        this.vehicleRepository = new VehicleRepository();
    }

    public void addVehicle(Vehicule vehicle) {

        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle cannot be null"
            );
        }

        List<Vehicule> vehicles = vehicleRepository.findAll();

        for (Vehicule existingVehicle : vehicles) {

            if (existingVehicle.getId().equals(vehicle.getId())) {
                throw new IllegalArgumentException(
                        "Vehicle ID already exists"
                );
            }
        }

        vehicleRepository.save(vehicle);

        System.out.println("Vehicle added successfully!");
    }

    public List<Vehicule> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicule findVehicleById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Vehicle ID cannot be empty"
            );
        }

        List<Vehicule> vehicles = vehicleRepository.findAll();

        for (Vehicule vehicle : vehicles) {

            if (vehicle.getId().equals(id)) {
                return vehicle;
            }
        }

        throw new IllegalArgumentException(
                "Vehicle not found: " + id
        );
    }


    public String generateNextVehicleId() {
        List<Vehicule> vehicles = getAllVehicles();

        int number = 1;

        while (true) {
            String newId = String.format("V%03d", number);

            boolean exists = vehicles.stream()
                    .anyMatch(vehicle -> vehicle.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }


}