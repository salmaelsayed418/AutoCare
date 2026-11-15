
package com.autocare.repository;

import com.autocare.model.Mechanic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class MechanicRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/mechanics.csv"
    );

    public MechanicRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "id,firstName,lastName,specialization\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize mechanics file", e);
        }
    }

    public void save(Mechanic mechanic) {

        if (mechanic == null) {
            throw new IllegalArgumentException("Mechanic cannot be null");
        }

        String line = mechanic.getId() + ","
                + mechanic.getFirstName() + ","
                + mechanic.getLastName() + ","
                + mechanic.getSpecialization() + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save mechanic", e);
        }
    }

    public List<Mechanic> findAll() {

        List<Mechanic> mechanics = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 4) {
                    continue;
                }

                Mechanic mechanic = new Mechanic(
                        data[0],
                        data[1],
                        data[2],
                        data[3]
                );

                mechanics.add(mechanic);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not read mechanics file", e);
        }

        return mechanics;
    }


    public String generateNextMechanicId() {
        List<Mechanic> mechanics = findAll();

        int number = 1;

        while (true) {
            String newId = String.format("M%03d", number);

            boolean exists = mechanics.stream()
                    .anyMatch(mechanic -> mechanic.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }

}
