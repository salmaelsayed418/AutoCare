package com.autocare;

import com.autocare.model.*;
import com.autocare.payment.*;
import com.autocare.repository.*;
import com.autocare.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CustomerService customerService = new CustomerService();
    private static final VehicleService vehicleService = new VehicleService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final RepairService repairService = new RepairService();
    private static final InventoryService inventoryService = new InventoryService();
    private static final InvoiceService invoiceService = new InvoiceService();
    private static final PaymentService paymentService =
            new PaymentService(new InvoiceRepository());

    private static final MechanicRepository mechanicRepository = new MechanicRepository();
    private static final ServiceOperationRepository serviceRepository = new ServiceOperationRepository();
    private static final PartRepository partRepository = new PartRepository();
    private static final RepairOrderRepository repairOrderRepository = new RepairOrderRepository();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("********************************");
        System.out.println("WELCOME TO AUTOCARE");
        System.out.println("********************************");

        while (running) {

            displayMainMenu();

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        customerMenu();
                        break;

                    case 2:
                        vehicleMenu();
                        break;

                    case 3:
                        appointmentMenu();
                        break;

                    case 4:
                        repairOrderMenu();
                        break;

                    case 5:
                        inventoryMenu();
                        break;

                    case 6:
                        invoiceMenu();
                        break;

                    case 7:
                        paymentMenu();
                        break;

                    case 8:
                        running = false;
                        System.out.println("Thank you for using AutoCare!");
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // ================= MAIN MENU =================

    private static void displayMainMenu() {

        System.out.println("\n----------- MAIN MENU -----------");
        System.out.println("1. Manage Customers");
        System.out.println("2. Manage Vehicles");
        System.out.println("3. Manage Appointments");
        System.out.println("4. Manage Repair Orders");
        System.out.println("5. Manage Inventory");
        System.out.println("6. Manage Invoices");
        System.out.println("7. Process Payments");
        System.out.println("8. Exit");
        System.out.println("---------------------------------");
    }

    // ================= CUSTOMER MENU =================

    private static void customerMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n-------- CUSTOMER MENU --------");
            System.out.println("1. Add customer");
            System.out.println("2. Display customers");
            System.out.println("3. Search customer");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addCustomer();
                        break;

                    case 2:
                        for (Customer c : customerService.getAllCustomers()) {
                            System.out.println(c.getId() + " - "
                                    + c.getFullName() + " - " + c.getEmail());
                        }
                        break;

                    case 3:
                        Customer customer = customerService.findCustomerById(
                                readText("Customer ID: "));
                        System.out.println(customer.getFullName());
                        System.out.println(customer.getEmail());
                        System.out.println(customer.getPhoneNumber());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }



    private static void addCustomer() {
        System.out.println("\n--- ADD CUSTOMER ---");

        String firstName = readText("First name: ");
        if (!firstName.matches("[a-zA-ZÀ-ÿ '-]+")) {
            System.out.println("Error: First name must contain only letters");
            return;
        }

        String lastName = readText("Last name: ");
        if (!lastName.matches("[a-zA-ZÀ-ÿ '-]+")) {
            System.out.println("Error: Last name must contain only letters");
            return;
        }

        String email = readText("Email: ");
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            System.out.println("Error: Invalid email format");
            return;
        }

        if (customerService.emailExists(email)) {
            System.out.println("Error: Email already exists");
            return;
        }

        String phone = readText("Phone number: ");
        if (!phone.matches("\\+?[0-9]{8,15}")) {
            System.out.println("Error: Phone number must contain 8 to 15 digits");
            return;
        }

        if (customerService.phoneExists(phone)) {
            System.out.println("Error: Phone number already exists");
            return;
        }

        String id = customerService.generateNextCustomerId();

        Customer customer = new Customer(id, firstName, lastName, email, phone);
        customerService.addCustomer(customer);

        System.out.println("Customer added successfully! ID: " + id);
    }



    // ================= VEHICLE MENU =================

    private static void vehicleMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n--------- VEHICLE MENU ---------");
            System.out.println("1. Add vehicle");
            System.out.println("2. Display vehicles");
            System.out.println("3. Search vehicle");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addVehicle();
                        break;

                    case 2:
                        for (Vehicule v : vehicleService.getAllVehicles()) {
                            System.out.println(v.getId() + " - "
                                    + v.getBrand() + " " + v.getModel()
                                    + " - Fee: " + v.calculateDiagnosticFee());
                        }
                        break;

                    case 3:
                        Vehicule vehicle = vehicleService.findVehicleById(
                                readText("Vehicle ID: "));
                        System.out.println(vehicle.getBrand() + " "
                                + vehicle.getModel());
                        System.out.println("Diagnostic fee: "
                                + vehicle.calculateDiagnosticFee());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addVehicle() {

        String id = vehicleService.generateNextVehicleId();
        String brand = readText("Brand: ");
        String model = readText("Model: ");
        int year = readInt("Year: ");
        String registration = readText("Registration number: ");
        int mileage = readInt("Mileage: ");

        System.out.println("1. Electric");
        System.out.println("2. Combustion");
        System.out.println("3. Hybrid");

        int type = readInt("Vehicle type: ");

        Vehicule vehicle;

        switch (type) {

            case 1:
                double battery = readDouble("Battery capacity: ");
                vehicle = new ElectricVehicle(
                        id, brand, model, year, registration, mileage, battery);
                break;

            case 2:
                String fuel = readText("Fuel type: ");
                vehicle = new CombustionVehicle(
                        id, brand, model, year, registration, mileage, fuel);
                break;

            case 3:
                double hybridBattery = readDouble("Battery capacity: ");
                String hybridFuel = readText("Fuel type: ");
                vehicle = new HybridVehicle(
                        id, brand, model, year, registration,
                        mileage, hybridBattery, hybridFuel);
                break;

            default:
                System.out.println("Invalid vehicle type.");
                return;
        }

        vehicleService.addVehicle(vehicle);
        System.out.println("Vehicle added successfully! ID: " + id);
    }

    // ================= APPOINTMENT MENU =================

    private static void appointmentMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n------- APPOINTMENT MENU -------");
            System.out.println("1. Add appointment");
            System.out.println("2. Display appointments");
            System.out.println("3. Search appointment");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addAppointment();
                        break;

                    case 2:
                        for (Appointment a : appointmentService.getAllAppointments()) {
                            System.out.println(a.getId() + " - "
                                    + a.getDescription() + " - "
                                    + a.getStatus());
                        }
                        break;

                    case 3:
                        Appointment appointment = appointmentService
                                .findAppointmentById(readText("Appointment ID: "));
                        System.out.println("Description: "
                                + appointment.getDescription());
                        System.out.println("Status: " + appointment.getStatus());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addAppointment() {

        System.out.println("\n--- CREATE APPOINTMENT ---");

        Customer customer = chooseCustomer();
        Vehicule vehicle = chooseVehicle();
        Mechanic mechanic = chooseMechanic();

        String id = appointmentService.generateNextAppointmentId();
        String description = readText("Description: ");

        LocalDateTime date = LocalDateTime.parse(
                readText("Date (YYYY-MM-DDTHH:MM): ")
        );

        Appointment appointment = new Appointment(
                id, customer, vehicle, mechanic, date, description
        );

        appointmentService.addAppointment(appointment);
        System.out.println("Appointment added successfully! ID: " + id);
    }

    private static Customer chooseCustomer() {

        List<Customer> customers = customerService.getAllCustomers();

        System.out.println("\n--- AVAILABLE CUSTOMERS ---");

        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);

            System.out.println((i + 1) + ". "
                    + c.getFullName() + " (" + c.getId() + ")");
        }

        if (customers.isEmpty()) {
            throw new IllegalArgumentException("No customers available.");
        }

        int choice = readInt("Choose a customer: ");

        if (choice < 1 || choice > customers.size()) {
            throw new IllegalArgumentException("Invalid customer choice.");
        }

        return customers.get(choice - 1);
    }

    private static Vehicule chooseVehicle() {

        List<Vehicule> vehicles = vehicleService.getAllVehicles();

        System.out.println("\n--- AVAILABLE VEHICLES ---");

        for (int i = 0; i < vehicles.size(); i++) {
            Vehicule v = vehicles.get(i);

            System.out.println((i + 1) + ". "
                    + v.getBrand() + " " + v.getModel()
                    + " (" + v.getId() + ")");
        }

        if (vehicles.isEmpty()) {
            throw new IllegalArgumentException("No vehicles available.");
        }

        int choice = readInt("Choose a vehicle: ");

        if (choice < 1 || choice > vehicles.size()) {
            throw new IllegalArgumentException("Invalid vehicle choice.");
        }

        return vehicles.get(choice - 1);
    }

    private static Mechanic chooseMechanic() {

        List<Mechanic> mechanics = mechanicRepository.findAll();

        System.out.println("\n--- AVAILABLE MECHANICS ---");

        for (int i = 0; i < mechanics.size(); i++) {
            Mechanic m = mechanics.get(i);

            System.out.println((i + 1) + ". "
                    + m.getFullName() + " (" + m.getId() + ")");
        }

        if (mechanics.isEmpty()) {
            throw new IllegalArgumentException("No mechanics available.");
        }

        int choice = readInt("Choose a mechanic: ");

        if (choice < 1 || choice > mechanics.size()) {
            throw new IllegalArgumentException("Invalid mechanic choice.");
        }

        return mechanics.get(choice - 1);
    }

    // ================= REPAIR ORDER MENU =================

    private static void repairOrderMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n-------- REPAIR ORDER MENU --------");
            System.out.println("1. Create repair order");
            System.out.println("2. Display repair orders");
            System.out.println("3. Search repair order");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addRepairOrder();
                        break;

                    case 2:
                        for (RepairOrder r : repairService.getAllRepairOrders()) {
                            System.out.println(r.getId() + " - "
                                    + r.getDescription() + " - Total: "
                                    + r.calculateTotalCost());
                        }
                        break;

                    case 3:
                        RepairOrder order = repairService.findRepairOrderById(
                                readText("Repair order ID: "));
                        System.out.println("Description: " + order.getDescription());
                        System.out.println("Total cost: " + order.calculateTotalCost());
                        System.out.println("Status: " + order.getStatus());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addRepairOrder() {

        String id = repairService.generateNextRepairOrderId();

        Customer customer = chooseCustomer();
        Vehicule vehicle = chooseVehicle();
        Mechanic mechanic = chooseMechanic();

        String description = readText("Description: ");

        RepairOrder order = new RepairOrder(
                id, customer, vehicle, mechanic, description);

        List<ServiceOperation> services = serviceRepository.findAll();

        if (!services.isEmpty()) {
            System.out.println("Available services:");
            for (ServiceOperation s : services) {
                System.out.println(s.getId() + " - " + s.getName()
                        + " - " + s.getPrice());
            }

            String serviceId = readText("Service ID to add: ");

            ServiceOperation service = services.stream()
                    .filter(s -> s.getId().equals(serviceId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Service not found"));

            order.addService(service);
        }

        List<Part> parts = partRepository.findAll();

        if (!parts.isEmpty()) {
            System.out.println("Available parts:");
            for (Part p : parts) {
                System.out.println(p.getId() + " - " + p.getName()
                        + " - Stock: " + p.getStockQuantity());
            }

            String partId = readText("Part ID to use: ");
            int quantity = readInt("Quantity: ");

            Part part = parts.stream()
                    .filter(p -> p.getId().equals(partId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Part not found"));

            inventoryService.validateStock(partId, quantity);

            order.addPartUsage(new PartUsage(part, quantity));
        }

        repairService.createRepairOrder(order);

        if (!order.getPartsUsed().isEmpty()) {
            PartUsage usage = order.getPartsUsed().get(0);

            inventoryService.consumePart(
                    usage.getPart().getId(),
                    usage.getQuantity()
            );
        }
        System.out.println("Repair order created successfully! ID: " + id);
        System.out.println("Repair order total: " + order.calculateTotalCost());
    }

    // ================= INVENTORY MENU =================

    private static void inventoryMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n--------- INVENTORY MENU ---------");
            System.out.println("1. Add part");
            System.out.println("2. Display parts");
            System.out.println("3. Search part");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addPart();
                        break;

                    case 2:
                        for (Part p : inventoryService.getAllParts()) {
                            System.out.println(p.getId() + " - "
                                    + p.getName() + " - Price: "
                                    + p.getUnitPrice() + " - Stock: "
                                    + p.getStockQuantity());
                        }
                        break;

                    case 3:
                        Part part = inventoryService.findPartById(
                                readText("Part ID: "));
                        System.out.println(part.getName());
                        System.out.println("Price: " + part.getUnitPrice());
                        System.out.println("Stock: " + part.getStockQuantity());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addPart() {

        String id = inventoryService.generateNextPartId();

        Part part = new Part(
                id,
                readText("Part name: "),
                readText("Description: "),
                readDouble("Unit price: "),
                readInt("Stock quantity: ")
        );

        inventoryService.addPart(part);
        System.out.println("Part added successfully! ID: " + id);
    }

    // ================= INVOICE MENU =================

    private static void invoiceMenu() {

        boolean back = false;

        while (!back) {

            System.out.println("\n---------- INVOICE MENU ----------");
            System.out.println("1. Create invoice");
            System.out.println("2. Display invoices");
            System.out.println("3. Search invoice");
            System.out.println("4. Back");

            try {
                int choice = readInt("Choose an option: ");

                switch (choice) {

                    case 1:
                        addInvoice();
                        break;

                    case 2:
                        for (Invoice i : invoiceService.getAllInvoices()) {
                            System.out.println(i.getId() + " - Amount: "
                                    + i.getTotalAmount() + " - Status: "
                                    + i.getPaymentStatus());
                        }
                        break;

                    case 3:
                        Invoice invoice = invoiceService.findInvoiceById(
                                readText("Invoice ID: "));
                        System.out.println("Amount: " + invoice.getTotalAmount());
                        System.out.println("Payment status: "
                                + invoice.getPaymentStatus());
                        break;

                    case 4:
                        back = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addInvoice() {

        String orderId = readText("Repair order ID: ");

        RepairOrder order = repairService.findRepairOrderById(orderId);

        String id = invoiceService.generateNextInvoiceId();

        Invoice invoice = new Invoice(id, order);

        invoiceService.createInvoice(invoice);
        System.out.println("Invoice created successfully! ID: " + id);
        System.out.println("Invoice amount: " + invoice.getTotalAmount());
    }

    // ================= PAYMENT MENU =================

    private static void paymentMenu() {

        try {
            String invoiceId = readText("Invoice ID to pay: ");

            Invoice invoice = invoiceService.findInvoiceById(invoiceId);

            if ("PAID".equals(invoice.getPaymentStatus())) {
                throw new IllegalStateException("Invoice has already been paid");
            }

            System.out.println("Amount to pay: "
                    + invoice.getTotalAmount() + " euros");

            System.out.println("1. Credit card");
            System.out.println("2. Cash");
            System.out.println("3. Bank transfer");

            int choice = readInt("Choose payment method: ");

            PaymentMethod method;

            switch (choice) {

                case 1:
                    method = new CardPayment(
                            readText("Card holder: "),
                            readText("Card number (16 digits): ")
                    );
                    break;

                case 2:
                    method = new CashPayment(
                            readDouble("Amount received: ")
                    );
                    break;

                case 3:
                    method = new BankTransferPayment(
                            readText("Account holder: "),
                            readText("IBAN: ")
                    );
                    break;

                default:
                    System.out.println("Invalid payment method.");
                    return;
            }

            paymentService.payInvoice(invoice, method);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Payment error: " + e.getMessage());
        }
    }

    // ================= INPUT HELPERS =================

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private static double readDouble(String message) {
        System.out.print(message);
        return Double.parseDouble(scanner.nextLine().trim());
    }
}
