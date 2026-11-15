
package com.autocare.repository;

import com.autocare.model.ServiceOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ServiceOperationRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/services.csv"
    );

    public ServiceOperationRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "id,name,description,price,estimatedDuration\n");
            }

            initializeDefaultServices();

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize services file", e);
        }
    }

    private void initializeDefaultServices() {

        List<ServiceOperation> defaults = List.of(
                new ServiceOperation("S001", "Battery check",
                        "Check the condition of the vehicle battery", 60, 30),
                new ServiceOperation("S002", "Oil change",
                        "Replace the engine oil", 80, 45),
                new ServiceOperation("S003", "Brake pads replacement",
                        "Replace worn brake pads", 120, 60),
                new ServiceOperation("S004", "Brake disc replacement",
                        "Replace worn brake discs", 180, 90),
                new ServiceOperation("S005", "Tire replacement",
                        "Replace vehicle tires", 100, 60),
                new ServiceOperation("S006", "Tire balancing",
                        "Balance the vehicle wheels", 40, 30),
                new ServiceOperation("S007", "Engine diagnostic",
                        "Check the engine for faults", 70, 45),
                new ServiceOperation("S008", "Air conditioning maintenance",
                        "Check and maintain the air conditioning system", 90, 60),
                new ServiceOperation("S009", "Battery replacement",
                        "Replace the vehicle battery", 150, 45),
                new ServiceOperation("S010", "Engine oil and filter change",
                        "Replace engine oil and oil filter", 100, 60),
                new ServiceOperation("S011", "Clutch replacement",
                        "Replace the vehicle clutch", 500, 240),
                new ServiceOperation("S012", "Vehicle inspection",
                        "Perform a general vehicle inspection", 50, 30)
        );

        List<ServiceOperation> existingServices = findAll();

        for (ServiceOperation service : defaults) {

            boolean exists = existingServices.stream()
                    .anyMatch(existing ->
                            existing.getId().equals(service.getId()));

            if (!exists) {
                save(service);
            }
        }
    }

    public void save(ServiceOperation service) {

        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        String line = service.getId() + ","
                + service.getName() + ","
                + service.getDescription() + ","
                + service.getPrice() + ","
                + service.getEstimatedDuration() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save service", e);
        }
    }

    public List<ServiceOperation> findAll() {

        List<ServiceOperation> services = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 5) {
                    continue;
                }

                ServiceOperation service = new ServiceOperation(
                        data[0],
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Integer.parseInt(data[4])
                );

                services.add(service);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not read services file", e);
        }

        return services;
    }

    public String generateNextServiceOperationId() {

        List<ServiceOperation> services = findAll();

        int number = 1;

        while (true) {
            String newId = String.format("S%03d", number);

            boolean exists = services.stream()
                    .anyMatch(service -> service.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }
}
