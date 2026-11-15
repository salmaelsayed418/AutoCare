
package com.autocare.payment;

public class CashPayment implements PaymentMethod {

    private double amountReceived;

    public CashPayment(double amountReceived) {

        if (amountReceived <= 0) {
            throw new IllegalArgumentException("Amount received must be positive");
        }

        this.amountReceived = amountReceived;
    }

    @Override
    public String getName() {
        return "Cash";
    }

    @Override
    public void processPayment(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amountReceived < amount) {
            throw new IllegalArgumentException("Insufficient cash received");
        }

        double change = amountReceived - amount;

        System.out.println("Cash payment of " + amount + " euros received.");
        System.out.println("Change: " + change + " euros.");
        System.out.println("Payment completed successfully.");
    }
}
