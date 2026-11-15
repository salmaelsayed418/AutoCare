package com.autocare.service;

import com.autocare.model.Invoice;
import com.autocare.repository.InvoiceRepository;

import java.util.List;

public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService() {
        this.invoiceRepository = new InvoiceRepository();
    }

    public void createInvoice(Invoice invoice) {

        if (invoice == null) {
            throw new IllegalArgumentException(
                    "Invoice cannot be null"
            );
        }

        List<Invoice> invoices = invoiceRepository.findAll();

        for (Invoice existingInvoice : invoices) {

            if (existingInvoice.getId().equals(invoice.getId())) {
                throw new IllegalArgumentException(
                        "Invoice ID already exists"
                );
            }
        }

        invoiceRepository.save(invoice);

        System.out.println("Invoice created successfully!");
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice findInvoiceById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Invoice ID cannot be empty"
            );
        }

        List<Invoice> invoices = invoiceRepository.findAll();

        for (Invoice invoice : invoices) {

            if (invoice.getId().equals(id)) {
                return invoice;
            }
        }

        throw new IllegalArgumentException(
                "Invoice not found: " + id
        );
    }


    public String generateNextInvoiceId() {
        List<Invoice> invoices = getAllInvoices();

        int number = 1;

        while (true) {
            String newId = String.format("I%03d", number);

            boolean exists = invoices.stream()
                    .anyMatch(invoice -> invoice.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }

}