
package com.autocare.payment;

public interface PaymentMethod {

    String getName();

    void processPayment(double amount);
}
