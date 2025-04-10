package com.omkcodes.cab_booking.controller;

import com.omkcodes.cab_booking.model.Driver;
import com.omkcodes.cab_booking.service.DriverService;
import com.omkcodes.cab_booking.exception.InvalidDriverIDException;

import java.util.Optional;
import java.util.Scanner;

public class DriverController {
    private final Scanner scanner;
    private final DriverService driverService;

    public DriverController(Scanner scanner, DriverService driverService) {
        this.scanner = scanner;
        this.driverService = driverService;
    }
    public void run() {
        int option;
        do {
            displayMenu();
            option = getIntInput("Enter your choice:");

            switch (option) {
                case 1 -> addNewDriver();
                case 2 -> driverService.showAllDrivers();
                case 3 -> displayDriverDetails();
                case 9 -> System.out.println("Going back to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }
    private void displayMenu() {
        System.out.println("""
                Please select an option from the list below:
                1. Add a new driver
                2. Show all drivers
                3. Display driver details
                9. Go back to main menu
                """);
    }
    private void addNewDriver() {
        try {
            String driverId = getStringInput("Enter Driver ID:");
            String driverName = getStringInput("Enter Driver Name:");
            String phone = getStringInput("Enter Phone Number:");
            String licenseNumber = getStringInput("Enter License Number:");
            int totalTrips = getIntInput("Enter Total Trips:");
            boolean onlineStatus = getBooleanInput("Is the driver online? (true/false):");
            String statusInput = getStringInput("Enter Driver Status (ACTIVE/INACTIVE):");

            Driver newDriver = driverService.createNewDriver(
                    driverId, driverName, phone, licenseNumber, totalTrips, onlineStatus, statusInput
            );

            System.out.println("Driver added successfully: " + newDriver);
        } catch (InvalidDriverIDException e) {
            System.out.println("Error adding driver: " + e.getMessage());
        }
    }
    private void displayDriverDetails() {
        String driverId = getStringInput("Enter Driver ID to display details:");
        Optional.ofNullable(driverService.getDriverList().get(driverId))
                .ifPresentOrElse(
                        driverService::displayDriverDetails,
                        () -> System.out.println("Driver not found.")
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
    private boolean getBooleanInput(String message) {
        while (true) {
            System.out.print(message + " ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
        }
    }
}
