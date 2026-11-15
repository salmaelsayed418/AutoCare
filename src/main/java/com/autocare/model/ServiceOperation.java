
package com.autocare.model;

public class ServiceOperation {

    private String id;
    private String name;
    private String description;
    private double price;
    private int estimatedDuration;

    public ServiceOperation(String id, String name, String description,
                            double price, int estimatedDuration) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Service ID cannot be empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Service name cannot be empty");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (estimatedDuration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.estimatedDuration = estimatedDuration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.price = newPrice;
    }
}
