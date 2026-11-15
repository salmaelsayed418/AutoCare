package com.autocare.service;

import com.autocare.model.Customer;
import com.autocare.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    public boolean emailExists(String email) {
        return customerRepository.findAll().stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }

    public boolean phoneExists(String phone) {
        return customerRepository.findAll().stream()
                .anyMatch(customer -> customer.getPhoneNumber().equals(phone));
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        boolean idExists = getAllCustomers().stream()
                .anyMatch(c -> c.getId().equals(customer.getId()));

        if (idExists) {
            throw new IllegalArgumentException("Customer ID already exists");
        }

        if (emailExists(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (phoneExists(customer.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID cannot be empty"
            );
        }

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {

            if (customer.getId().equals(id)) {
                return customer;
            }
        }

        throw new IllegalArgumentException(
                "Customer not found: " + id
        );
    }

    public String generateNextCustomerId() {
        List<Customer> customers = getAllCustomers();

        int number = 1;
        String newId;

        do {
            newId = String.format("C%03d", number);
            String finalId = newId;

            boolean exists = customers.stream()
                    .anyMatch(customer -> customer.getId().equals(finalId));

            if (!exists) {
                return newId;
            }

            number++;

        } while (true);
    }
}
