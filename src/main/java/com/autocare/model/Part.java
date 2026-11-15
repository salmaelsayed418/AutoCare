
package com.autocare.model;

public class Part {

    private String id;
    private String name;
    private String description;
    private double unitPrice;
    private int stockQuantity;

    public Part(String id, String name, String description,
                double unitPrice, int stockQuantity) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Part ID cannot be empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Part name cannot be empty");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (unitPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.unitPrice = newPrice;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (quantity > stockQuantity) {
            throw new IllegalArgumentException("Not enough parts in stock");
        }

        this.stockQuantity -= quantity;
    }
}
