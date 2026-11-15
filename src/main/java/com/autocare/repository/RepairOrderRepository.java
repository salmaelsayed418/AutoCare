package com.autocare.repository;

import com.autocare.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepairOrderRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/repair_orders.csv"
    );

    private final CustomerRepository customerRepository =
            new CustomerRepository();

    private final VehicleRepository vehicleRepository =
            new VehicleRepository();

    private final MechanicRepository mechanicRepository =
            new MechanicRepository();

    private final ServiceOperationRepository serviceRepository =
            new ServiceOperationRepository();

    private final PartRepository partRepository =
            new PartRepository();

    public RepairOrderRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);

                Files.writeString(
                        filePath,
                        "id,customerId,vehicleId,mechanicId,description,status,serviceIds,partUsages\n"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not initialize repair orders file", e
            );
        }
    }

    public void save(RepairOrder order) {

        if (order == null) {
            throw new IllegalArgumentException(
                    "Repair order cannot be null"
            );
        }

        StringBuilder serviceIds = new StringBuilder();

        for (ServiceOperation service : order.getServices()) {
            if (serviceIds.length() > 0) {
                serviceIds.append(";");
            }
            serviceIds.append(service.getId());
        }

        StringBuilder partUsages = new StringBuilder();

        for (PartUsage usage : order.getPartsUsed()) {
            if (partUsages.length() > 0) {
                partUsages.append(";");
            }

            partUsages.append(usage.getPart().getId())
                    .append(":")
                    .append(usage.getQuantity());
        }

        String line = order.getId() + ","
                + order.getCustomer().getId() + ","
                + order.getVehicle().getId() + ","
                + order.getMechanic().getId() + ","
                + order.getDescription() + ","
                + order.getStatus() + ","
                + serviceIds + ","
                + partUsages + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not save repair order", e
            );
        }
    }

    public List<RepairOrder> findAll() {

        List<RepairOrder> orders = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            List<Customer> customers = customerRepository.findAll();
            List<Vehicule> vehicles = vehicleRepository.findAll();
            List<Mechanic> mechanics = mechanicRepository.findAll();
            List<ServiceOperation> services = serviceRepository.findAll();
            List<Part> parts = partRepository.findAll();

            Map<String, Customer> customerMap = new HashMap<>();
            Map<String, Vehicule> vehicleMap = new HashMap<>();
            Map<String, Mechanic> mechanicMap = new HashMap<>();
            Map<String, ServiceOperation> serviceMap = new HashMap<>();
            Map<String, Part> partMap = new HashMap<>();

            for (Customer customer : customers) {
                customerMap.put(customer.getId(), customer);
            }

            for (Vehicule vehicle : vehicles) {
                vehicleMap.put(vehicle.getId(), vehicle);
            }

            for (Mechanic mechanic : mechanics) {
                mechanicMap.put(mechanic.getId(), mechanic);
            }

            for (ServiceOperation service : services) {
                serviceMap.put(service.getId(), service);
            }

            for (Part part : parts) {
                partMap.put(part.getId(), part);
            }

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 8) {
                    continue;
                }

                Customer customer = customerMap.get(data[1]);
                Vehicule vehicle = vehicleMap.get(data[2]);
                Mechanic mechanic = mechanicMap.get(data[3]);

                if (customer == null || vehicle == null || mechanic == null) {
                    throw new IllegalStateException(
                            "Unknown customer, vehicle or mechanic"
                    );
                }

                RepairOrder order = new RepairOrder(
                        data[0],
                        customer,
                        vehicle,
                        mechanic,
                        data[4]
                );

                order.updateStatus(data[5]);

                // Reconstruct the services
                if (!data[6].isEmpty()) {
                    String[] ids = data[6].split(";");

                    for (String id : ids) {
                        ServiceOperation service = serviceMap.get(id);

                        if (service == null) {
                            throw new IllegalStateException(
                                    "Unknown service: " + id
                            );
                        }

                        order.addService(service);
                    }
                }

                // Reconstruct the parts used
                if (!data[7].isEmpty()) {
                    String[] usages = data[7].split(";");

                    for (String usage : usages) {
                        String[] values = usage.split(":");

                        if (values.length != 2) {
                            continue;
                        }

                        Part part = partMap.get(values[0]);
                        int quantity = Integer.parseInt(values[1]);

                        if (part == null) {
                            throw new IllegalStateException(
                                    "Unknown part: " + values[0]
                            );
                        }

                        order.addPartUsage(
                                new PartUsage(part, quantity)
                        );
                    }
                }

                orders.add(order);
            }

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(
                    "Could not read repair orders file", e
            );
        }

        return orders;
    }
}