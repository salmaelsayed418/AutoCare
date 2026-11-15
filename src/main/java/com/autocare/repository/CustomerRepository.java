
package com.autocare.repository;

import com.autocare.model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/customers.csv"
    );

    public CustomerRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "id,firstName,lastName,email,phoneNumber\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize customers file", e);
        }
    }

    public void save(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        String line = customer.getId() + ","
                + customer.getFirstName() + ","
                + customer.getLastName() + ","
                + customer.getEmail() + ","
                + customer.getPhoneNumber() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save customer", e);
        }
    }

    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 5) {
                    continue;
                }

                Customer customer = new Customer(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                customers.add(customer);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not read customers file", e);
        }

        return customers;
    }
}
