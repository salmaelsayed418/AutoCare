package com.autocare.model;

import java.time.LocalDate;

public class Invoice {

    private String id;
    private RepairOrder repairOrder;
    private LocalDate issueDate;
    private double totalAmount;
    private String paymentStatus;

    // Constructor used to create a new invoice
    public Invoice(String id, RepairOrder repairOrder) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invoice ID cannot be empty");
        }

        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order cannot be null");
        }

        this.id = id;
        this.repairOrder = repairOrder;
        this.issueDate = LocalDate.now();
        this.totalAmount = repairOrder.calculateTotalCost();
        this.paymentStatus = "UNPAID";
    }

    // Constructor used to reconstruct an invoice from CSV
    public Invoice(String id, RepairOrder repairOrder,
                   LocalDate issueDate, double totalAmount,
                   String paymentStatus) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Invoice ID cannot be empty");
        }

        if (repairOrder == null) {
            throw new IllegalArgumentException("Repair order cannot be null");
        }

        if (issueDate == null) {
            throw new IllegalArgumentException("Issue date cannot be null");
        }

        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }

        if (paymentStatus == null || paymentStatus.isBlank()) {
            throw new IllegalArgumentException("Payment status cannot be empty");
        }

        this.id = id;
        this.repairOrder = repairOrder;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void markAsPaid() {
        this.paymentStatus = "PAID";
    }

    public void markAsUnpaid() {
        this.paymentStatus = "UNPAID";
    }
}