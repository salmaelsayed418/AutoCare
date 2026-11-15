
package com.autocare.model;

import java.time.LocalDateTime;

public class Appointment {

    private String id;
    private Customer customer;
    private Vehicule vehicle;
    private Mechanic mechanic;
    private LocalDateTime appointmentDate;
    private String description;
    private String status;

    public Appointment(String id, Customer customer, Vehicule vehicle,
                       Mechanic mechanic, LocalDateTime appointmentDate,
                       String description) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Appointment ID cannot be empty");
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

        if (appointmentDate == null) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        this.id = id;
        this.customer = customer;
        this.vehicle = vehicle;
        this.mechanic = mechanic;
        this.appointmentDate = appointmentDate;
        this.description = description;
        this.status = "SCHEDULED";
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

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void reschedule(LocalDateTime newDate) {
        if (newDate == null) {
            throw new IllegalArgumentException("New date cannot be null");
        }

        this.appointmentDate = newDate;
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        this.status = newStatus;
    }
}
