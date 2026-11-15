package com.autocare.service;

import com.autocare.model.Appointment;
import com.autocare.repository.AppointmentRepository;

import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepository();
    }

    public void addAppointment(Appointment appointment) {

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment cannot be null"
            );
        }

        List<Appointment> appointments =
                appointmentRepository.findAll();

        for (Appointment existingAppointment : appointments) {

            if (existingAppointment.getId().equals(appointment.getId())) {
                throw new IllegalArgumentException(
                        "Appointment ID already exists"
                );
            }
        }

        appointmentRepository.save(appointment);

        System.out.println("Appointment added successfully!");
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment findAppointmentById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Appointment ID cannot be empty"
            );
        }

        List<Appointment> appointments =
                appointmentRepository.findAll();

        for (Appointment appointment : appointments) {

            if (appointment.getId().equals(id)) {
                return appointment;
            }
        }

        throw new IllegalArgumentException(
                "Appointment not found: " + id
        );
    }


    public String generateNextAppointmentId() {
        List<Appointment> appointments = getAllAppointments();

        int number = 1;

        while (true) {
            String newId = String.format("A%03d", number);

            boolean exists = appointments.stream()
                    .anyMatch(appointment -> appointment.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }

}