package com.autocare.service;

import com.autocare.model.Part;
import com.autocare.repository.PartRepository;
import com.autocare.exception.InsufficientStockException;

import java.util.List;

public class InventoryService {

    private final PartRepository partRepository;

    public InventoryService() {
        this(new PartRepository());
    }

    public InventoryService(PartRepository partRepository) {
        if (partRepository == null) {
            throw new IllegalArgumentException("PartRepository cannot be null");
        }

        this.partRepository = partRepository;
    }

    public void addPart(Part part) {

        if (part == null) {
            throw new IllegalArgumentException(
                    "Part cannot be null"
            );
        }

        List<Part> parts = partRepository.findAll();

        for (Part existingPart : parts) {

            if (existingPart.getId().equals(part.getId())) {
                throw new IllegalArgumentException(
                        "Part ID already exists"
                );
            }
        }

        partRepository.save(part);

        System.out.println("Part added to inventory successfully!");
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part findPartById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Part ID cannot be empty"
            );
        }

        List<Part> parts = partRepository.findAll();

        for (Part part : parts) {

            if (part.getId().equals(id)) {
                return part;
            }
        }

        throw new IllegalArgumentException(
                "Part not found: " + id
        );
    }

    public String generateNextPartId() {
        List<Part> parts = getAllParts();

        int number = 1;

        while (true) {
            String newId = String.format("P%03d", number);

            boolean exists = parts.stream()
                    .anyMatch(part -> part.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }



    public void validateStock(String partId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive"
            );
        }

        Part part = findPartById(partId);

        if (quantity > part.getStockQuantity()) {
            throw new InsufficientStockException(
                    "Not enough stock for " + part.getName()
                            + ". Available: " + part.getStockQuantity()
                            + ", requested: " + quantity
            );
        }
    }


    public void consumePart(String partId, int quantity) {

        Part part = findPartById(partId);

        part.removeStock(quantity);

        partRepository.update(part);

        System.out.println(
                quantity + " unit(s) of " + part.getName()
                        + " removed from inventory."
        );
    }


}