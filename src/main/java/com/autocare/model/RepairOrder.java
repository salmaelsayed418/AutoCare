
package com.autocare.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RepairOrder {

    private String id;
    private Customer customer;
    private Vehicule vehicle;
    private Mechanic mechanic;
    private LocalDate creationDate;
    private String description;
    private String status;

    private List<ServiceOperation> services;
    private List<PartUsage> partsUsed;

    public RepairOrder(String id, Customer customer, Vehicule vehicle,
                       Mechanic mechanic, String description) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Repair order ID cannot be empty");
        }

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        if (mechanic == null) {
            throw new IllegalArgumentException("Mechanic cannot be null");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mechanic = mechanic;
        this.creationDate = LocalDate.now();
        this.description = description;
        this.status = "OPEN";

        this.services = new ArrayList<>();
        this.partsUsed = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicule getVehicle() {
        return vehicle;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public List<ServiceOperation> getServices() {
        return new ArrayList<>(services);
    }

    public List<PartUsage> getPartsUsed() {
        return new ArrayList<>(partsUsed);
    }

    public void addService(ServiceOperation service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        services.add(service);
    }

    public void addPartUsage(PartUsage partUsage) {
        if (partUsage == null) {
            throw new IllegalArgumentException("Part usage cannot be null");
        }

        partsUsed.add(partUsage);
    }

    public double calculateTotalCost() {
        double total = 0;

        for (ServiceOperation service : services) {
            total += service.getPrice();
        }

        for (PartUsage part : partsUsed) {
            total += part.calculateTotalPrice();
        }

        return total;
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        this.status = newStatus;
    }

}
