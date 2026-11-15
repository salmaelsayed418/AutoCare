package com.autocare.repository;

import com.autocare.model.Appointment;
import com.autocare.model.Customer;
import com.autocare.model.Vehicule;
import com.autocare.model.Mechanic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/appointments.csv"
    );

    private final CustomerRepository customerRepository =
            new CustomerRepository();

    private final VehicleRepository vehicleRepository =
            new VehicleRepository();

    private final MechanicRepository mechanicRepository =
            new MechanicRepository();

    public AppointmentRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);

                Files.writeString(
                        filePath,
                        "id,customerId,vehicleId,mechanicId,appointmentDate,description,status\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not initialize appointments file", e
            );
        }
    }

    public void save(Appointment appointment) {

        if (appointment == null) {
            throw new IllegalArgumentException(
                    "Appointment cannot be null"
            );
        }

        String line = appointment.getId() + ","
                + appointment.getCustomer().getId() + ","
                + appointment.getVehicle().getId() + ","
                + appointment.getMechanic().getId() + ","
                + appointment.getAppointmentDate() + ","
                + appointment.getDescription() + ","
                + appointment.getStatus() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not save appointment", e
            );
        }
    }

    public List<Appointment> findAll() {

        List<Appointment> appointments = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            List<Customer> customers = customerRepository.findAll();
            List<Vehicule> vehicles = vehicleRepository.findAll();
            List<Mechanic> mechanics = mechanicRepository.findAll();

            Map<String, Customer> customerMap = new HashMap<>();
            Map<String, Vehicule> vehicleMap = new HashMap<>();
            Map<String, Mechanic> mechanicMap = new HashMap<>();

            for (Customer customer : customers) {
                customerMap.put(customer.getId(), customer);
            }

            for (Vehicule vehicle : vehicles) {
                vehicleMap.put(vehicle.getId(), vehicle);
            }

            for (Mechanic mechanic : mechanics) {
                mechanicMap.put(mechanic.getId(), mechanic);
            }

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 7) {
                    continue;
                }

                Customer customer = customerMap.get(data[1]);
                Vehicule vehicle = vehicleMap.get(data[2]);
                Mechanic mechanic = mechanicMap.get(data[3]);

                if (customer == null || vehicle == null || mechanic == null) {
                    throw new IllegalStateException(
                            "Appointment references unknown customer, vehicle or mechanic"
                    );
                }

                Appointment appointment = new Appointment(
                        data[0],
                        customer,
                        vehicle,
                        mechanic,
                        LocalDateTime.parse(data[4]),
                        data[5]
                );

                appointment.updateStatus(data[6]);

                appointments.add(appointment);
            }

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(
                    "Could not read appointments file", e
            );
        }

        return appointments;
    }
}