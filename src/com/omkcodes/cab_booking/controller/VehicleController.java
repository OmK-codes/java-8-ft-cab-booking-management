package com.omkcodes.cab_booking.controller;
import com.omkcodes.cab_booking.model.Vehicle;
import com.omkcodes.cab_booking.service.VehicleService;
import com.omkcodes.cab_booking.exception.InvalidVehicleIDException;
import java.util.Optional;
import java.util.Scanner;

public class VehicleController {
    private final Scanner scanner;
    private final VehicleService vehicleService;

    public VehicleController(Scanner scanner, VehicleService vehicleService) {
        this.scanner = scanner;
        this.vehicleService = vehicleService;
    }
    public void run() {
        int option;
        do {
            displayMenu();
            option = getIntInput("Enter your choice:");
            switch (option) {
                case 1 -> addNewVehicle();
                case 2 -> vehicleService.showAllVehicles();
                case 3 -> displayVehicleDetails();
                case 9 -> System.out.println("Going back to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }
    private void displayMenu() {
        System.out.println("""
                Please select an option from the list below:
                1. Add a new vehicle
                2. Show all vehicles
                3. Display vehicle details
                9. Go back to main menu
                """);
    }
    private void addNewVehicle() {
        try {
            String vehicleId = getStringInput("Enter Vehicle ID:");
            String model = getStringInput("Enter Vehicle Model:");
            String plateNumber = getStringInput("Enter License Plate Number:");
            String color = getStringInput("Enter Vehicle Color:");
            boolean availability = getBooleanInput("Is the vehicle available? (true/false):");
            int seatCapacity = getIntInput("Enter Seat Capacity:");
            double perKmRate = getDoubleInput("Enter Per KM Rate:");
            String status = getStringInput("Enter Vehicle Status (AVAILABLE/UNAVAILABLE):").toUpperCase();

            // Call the corrected method with appropriate parameters
            Vehicle newVehicle = vehicleService.createNewVehicle(
                    vehicleId, model, plateNumber, color, availability, seatCapacity, perKmRate, status
            );

            System.out.println("Vehicle added successfully: " + newVehicle);
        } catch (InvalidVehicleIDException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
    }
    private void displayVehicleDetails() {
        String vehicleId = getStringInput("Enter Vehicle ID to display details:");

        Optional.ofNullable(vehicleService.getVehicleList().get(vehicleId))
                .ifPresentOrElse(
                        vehicleService::displayVehicleDetails,
                        () -> System.out.println("Vehicle not found.")
                );
    }
    private String getStringInput(String message) {
        System.out.print(message + " ");
        return scanner.nextLine().trim();
    }

    private int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    private double getDoubleInput(String message) {
        while (true) {
            try {
                System.out.print(message + " ");
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
    private boolean getBooleanInput(String message) {
        while (true) {
            String input = getStringInput(message).toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
        }
    }
}