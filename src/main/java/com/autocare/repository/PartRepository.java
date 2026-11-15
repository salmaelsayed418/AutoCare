
package com.autocare.repository;

import com.autocare.model.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PartRepository {

    private final Path filePath;

    public PartRepository() {
        this(Path.of("src/main/resources/data/parts.csv"));
    }

    public PartRepository(Path filePath) {
        this.filePath = filePath;

        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "id,name,description,unitPrice,stockQuantity\n");
            }

            initializeDefaultParts();

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize parts file", e);
        }
    }

    private void initializeDefaultParts() {

        List<Part> defaultParts = List.of(

                new Part("P003", "Brake pads",
                        "Replacement brake pads", 35, 20),

                new Part("P004", "Brake discs",
                        "Replacement brake discs", 60, 10),

                new Part("P005", "Tires",
                        "Replacement vehicle tires", 80, 20),

                new Part("P006", "Engine oil",
                        "Engine oil for vehicle maintenance", 30, 30),

                new Part("P007", "Oil filter",
                        "Filter used to clean engine oil", 15, 25),

                new Part("P008", "Car battery",
                        "Replacement vehicle battery", 120, 10),

                new Part("P009", "Clutch kit",
                        "Complete replacement clutch kit", 250, 5),

                new Part("P010", "Air conditioning filter",
                        "Filter for the air conditioning system", 25, 15),

                new Part("P011", "Air filter",
                        "Engine air filter", 20, 20),

                new Part("P012", "Spark plugs",
                        "Replacement engine spark plugs", 12, 30)
        );

        List<Part> existingParts = findAll();

        for (Part part : defaultParts) {

            boolean exists = existingParts.stream()
                    .anyMatch(existing ->
                            existing.getId().equals(part.getId()));

            if (!exists) {
                save(part);
            }
        }
    }

    public void save(Part part) {

        if (part == null) {
            throw new IllegalArgumentException("Part cannot be null");
        }

        String line = part.getId() + ","
                + part.getName() + ","
                + part.getDescription() + ","
                + part.getUnitPrice() + ","
                + part.getStockQuantity() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save part", e);
        }
    }

    public List<Part> findAll() {

        List<Part> parts = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 5) {
                    continue;
                }

                Part part = new Part(
                        data[0],
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Integer.parseInt(data[4])
                );

                parts.add(part);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not read parts file", e);
        }

        return parts;
    }


    public void update(Part part) {

        if (part == null) {
            throw new IllegalArgumentException("Part cannot be null");
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            boolean found = false;

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length == 5 && data[0].equals(part.getId())) {

                    lines.set(i,
                            part.getId() + ","
                                    + part.getName() + ","
                                    + part.getDescription() + ","
                                    + part.getUnitPrice() + ","
                                    + part.getStockQuantity()
                    );

                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException(
                        "Part not found: " + part.getId()
                );
            }

            Files.write(filePath, lines);

        } catch (IOException e) {
            throw new RuntimeException("Could not update part", e);
        }
    }



}
