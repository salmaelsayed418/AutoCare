
package com.autocare.payment;

public class BankTransferPayment implements PaymentMethod {

    private String accountHolder;
    private String iban;

    public BankTransferPayment(String accountHolder, String iban) {

        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException("Account holder cannot be empty");
        }

        if (iban == null || iban.isBlank()) {
            throw new IllegalArgumentException("IBAN cannot be empty");
        }

        this.accountHolder = accountHolder;
        this.iban = iban;
    }

    @Override
    public String getName() {
        return "Bank Transfer";
    }

    @Override
    public void processPayment(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        System.out.println("Processing bank transfer of " + amount + " euros.");
        System.out.println("Account holder: " + accountHolder);
        System.out.println("Bank transfer completed successfully.");
    }
}
