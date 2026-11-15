
package com.autocare.repository;

import com.autocare.model.Vehicule;
import com.autocare.model.ElectricVehicle;
import com.autocare.model.CombustionVehicle;
import com.autocare.model.HybridVehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    private final Path filePath = Path.of(
            "src/main/resources/data/vehicles.csv"
    );

    public VehicleRepository() {
        try {
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                Files.writeString(filePath,
                        "id,brand,model,year,registrationNumber,mileage,type,batteryCapacity,fuelType\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize vehicles file", e);
        }
    }

    public void save(Vehicule vehicle) {

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }

        String type;
        String batteryCapacity = "";
        String fuelType = "";

        if (vehicle instanceof ElectricVehicle electric) {
            type = "ELECTRIC";
            batteryCapacity = String.valueOf(electric.getBatteryCapacity());

        } else if (vehicle instanceof CombustionVehicle combustion) {
            type = "COMBUSTION";
            fuelType = combustion.getFuelType();

        } else if (vehicle instanceof HybridVehicle hybrid) {
            type = "HYBRID";
            batteryCapacity = String.valueOf(hybrid.getBatteryCapacity());
            fuelType = hybrid.getFuelType();

        } else {
            throw new IllegalArgumentException("Unknown vehicle type");
        }

        String line = vehicle.getId() + ","
                + vehicle.getBrand() + ","
                + vehicle.getModel() + ","
                + vehicle.getYear() + ","
                + vehicle.getRegistrationNumber() + ","
                + vehicle.getMileage() + ","
                + type + ","
                + batteryCapacity + ","
                + fuelType + "\n";

        try {
            Files.writeString(
                    filePath,
                    line,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {
            throw new RuntimeException("Could not save vehicle", e);
        }
    }

    public List<Vehicule> findAll() {

        List<Vehicule> vehicles = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {

                String[] data = lines.get(i).split(",", -1);

                if (data.length != 9) {
                    continue;
                }

                String id = data[0];
                String brand = data[1];
                String model = data[2];
                int year = Integer.parseInt(data[3]);
                String registrationNumber = data[4];
                int mileage = Integer.parseInt(data[5]);

                Vehicule vehicle;

                switch (data[6]) {

                    case "ELECTRIC":
                        vehicle = new ElectricVehicle(
                                id, brand, model, year,
                                registrationNumber, mileage,
                                Double.parseDouble(data[7])
                        );
                        break;

                    case "COMBUSTION":
                        vehicle = new CombustionVehicle(
                                id, brand, model, year,
                                registrationNumber, mileage,
                                data[8]
                        );
                        break;

                    case "HYBRID":
                        vehicle = new HybridVehicle(
                                id, brand, model, year,
                                registrationNumber, mileage,
                                Double.parseDouble(data[7]),
                                data[8]
                        );
                        break;

                    default:
                        continue;
                }

                vehicles.add(vehicle);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not read vehicles file", e);
        }

        return vehicles;
    }
}
