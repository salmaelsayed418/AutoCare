
package com.autocare.model;

public class PartUsage {

    private Part part;
    private int quantity;
    private double unitPrice;

    public PartUsage(Part part, int quantity) {

        if (part == null) {
            throw new IllegalArgumentException("Part cannot be null");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        this.part = part;
        this.quantity = quantity;
        this.unitPrice = part.getUnitPrice();
    }

    public Part getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double calculateTotalPrice() {
        return unitPrice * quantity;
    }
}
