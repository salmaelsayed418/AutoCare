
package com.autocare.model;

public class Mechanic {

    private String id;
    private String firstName;
    private String lastName;
    private String specialization;

    public Mechanic(String id, String firstName, String lastName,
                    String specialization) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Mechanic ID cannot be empty");
        }

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (specialization == null || specialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
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

    public String getSpecialization() {
        return specialization;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void updateSpecialization(String newSpecialization) {
        if (newSpecialization == null || newSpecialization.isBlank()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }

        this.specialization = newSpecialization;
    }
}
