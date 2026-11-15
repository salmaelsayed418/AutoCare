package com.autocare.model;

public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer(String id, String firstName, String lastName,
                    String email, String phoneNumber) {

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber == null || newPhoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        this.phoneNumber = newPhoneNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
