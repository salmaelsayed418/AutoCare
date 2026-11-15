
package com.autocare.payment;

public class CardPayment implements PaymentMethod {

    private String cardHolder;
    private String cardNumber;

    public CardPayment(String cardHolder, String cardNumber) {

        if (cardHolder == null || cardHolder.isBlank()) {
            throw new IllegalArgumentException("Card holder cannot be empty");
        }

        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Card number must contain 16 digits");
        }

        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public void processPayment(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        System.out.println("Processing card payment of " + amount + " euros.");
        System.out.println("Payment completed successfully.");
    }
}

