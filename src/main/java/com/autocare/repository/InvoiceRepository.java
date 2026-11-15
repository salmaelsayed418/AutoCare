package com.autocare.repository;

import com.autocare.model.Invoice;
import com.autocare.model.RepairOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/invoices.csv"
    );

    private final RepairOrderRepository repairOrderRepository =
            new RepairOrderRepository();

    public InvoiceRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);

                Files.writeString(
                        filePath,
                        "id,repairOrderId,issueDate,totalAmount,paymentStatus\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not initialize invoices file", e
            );
        }
    }

    public void save(Invoice invoice) {

        if (invoice == null) {
            throw new IllegalArgumentException(
                    "Invoice cannot be null"
            );
        }

        String line = invoice.getId() + ","
                + invoice.getRepairOrder().getId() + ","
                + invoice.getIssueDate() + ","
                + invoice.getTotalAmount() + ","
                + invoice.getPaymentStatus() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not save invoice", e
            );
        }
    }


    public void update(Invoice invoice) {

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            boolean found = false;

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length == 5 && data[0].equals(invoice.getId())) {

                    lines.set(i,
                            invoice.getId() + ","
                                    + invoice.getRepairOrder().getId() + ","
                                    + invoice.getIssueDate() + ","
                                    + invoice.getTotalAmount() + ","
                                    + invoice.getPaymentStatus()
                    );

                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException(
                        "Invoice not found: " + invoice.getId()
                );
            }

            Files.write(filePath, lines);

        } catch (IOException e) {
            throw new RuntimeException("Could not update invoice", e);
        }
    }


    public List<Invoice> findAll() {

        List<Invoice> invoices = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            List<RepairOrder> orders =
                    repairOrderRepository.findAll();

            Map<String, RepairOrder> orderMap = new HashMap<>();

            for (RepairOrder order : orders) {
                orderMap.put(order.getId(), order);
            }

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 5) {
                    continue;
                }

                RepairOrder order = orderMap.get(data[1]);

                if (order == null) {
                    throw new IllegalStateException(
                            "Unknown repair order: " + data[1]
                    );
                }

                Invoice invoice = new Invoice(
                        data[0],
                        order,
                        LocalDate.parse(data[2]),
                        Double.parseDouble(data[3]),
                        data[4]
                );

                invoices.add(invoice);
            }

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(
                    "Could not read invoices file", e
            );
        }

        return invoices;
    }
}