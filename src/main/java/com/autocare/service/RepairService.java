package com.autocare.service;

import com.autocare.model.RepairOrder;
import com.autocare.repository.RepairOrderRepository;

import java.util.List;

public class RepairService {

    private final RepairOrderRepository repairOrderRepository;

    public RepairService() {
        this.repairOrderRepository = new RepairOrderRepository();
    }

    public void createRepairOrder(RepairOrder repairOrder) {

        if (repairOrder == null) {
            throw new IllegalArgumentException(
                    "Repair order cannot be null"
            );
        }

        List<RepairOrder> orders =
                repairOrderRepository.findAll();

        for (RepairOrder existingOrder : orders) {

            if (existingOrder.getId().equals(repairOrder.getId())) {
                throw new IllegalArgumentException(
                        "Repair order ID already exists"
                );
            }
        }

        repairOrderRepository.save(repairOrder);

        System.out.println("Repair order created successfully!");
    }

    public List<RepairOrder> getAllRepairOrders() {
        return repairOrderRepository.findAll();
    }

    public RepairOrder findRepairOrderById(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Repair order ID cannot be empty"
            );
        }

        List<RepairOrder> orders =
                repairOrderRepository.findAll();

        for (RepairOrder order : orders) {

            if (order.getId().equals(id)) {
                return order;
            }
        }

        throw new IllegalArgumentException(
                "Repair order not found: " + id
        );
    }


    public String generateNextRepairOrderId() {
        List<RepairOrder> orders = getAllRepairOrders();

        int number = 1;

        while (true) {
            String newId = String.format("R%03d", number);

            boolean exists = orders.stream()
                    .anyMatch(order -> order.getId().equals(newId));

            if (!exists) {
                return newId;
            }

            number++;
        }
    }

}