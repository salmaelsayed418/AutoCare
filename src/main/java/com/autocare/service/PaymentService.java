
package com.autocare.service;

import com.autocare.model.Invoice;
import com.autocare.payment.PaymentMethod;
import com.autocare.repository.InvoiceRepository;

public class PaymentService {

    private final InvoiceRepository invoiceRepository;

    public PaymentService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void payInvoice(Invoice invoice, PaymentMethod paymentMethod) {

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        if ("PAID".equals(invoice.getPaymentStatus())) {
            throw new IllegalStateException("Invoice has already been paid");
        }

        paymentMethod.processPayment(invoice.getTotalAmount());

        invoice.markAsPaid();

        invoiceRepository.update(invoice);

        System.out.println("Invoice " + invoice.getId() + " is now PAID.");
    }
}
