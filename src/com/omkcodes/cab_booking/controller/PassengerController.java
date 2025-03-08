package com.omkcodes.cab_booking.controller;
import com.omkcodes.cab_booking.model.Passenger;
import com.omkcodes.cab_booking.service.PassengerService;
import com.omkcodes.cab_booking.exception.InvalidPassengerIDException;
import java.util.Optional;
import java.util.Scanner;

public class PassengerController {
    private final Scanner scanner;
    private final PassengerService passengerService;
    public PassengerController(Scanner scanner, PassengerService passengerService) {
        this.scanner = scanner;
        this.passengerService = passengerService;
    }
    public void run() {
        int option;
        do {
            displayMenu();
            option = getIntInput("Enter your choice:");
            switch (option) {
                case 1 -> addNewPassenger();
                case 2 -> passengerService.showAllPassengers();
                case 3 -> displayPassengerDetails();
                case 9 -> System.out.println("Going back to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 9);
    }
    private void displayMenu() {
        System.out.println("""
                Please select an option from the list below:
                1. Add a new passenger
                2. Show all passengers
                3. Display passenger details
                9. Go back to main menu
                """);
    }
    private void addNewPassenger() {
        try {
            String passengerId = getStringInput("Enter Passenger ID:");
            String passengerName = getStringInput("Enter Passenger Name:");
            String phone = getStringInput("Enter Phone Number:");
            String email = getStringInput("Enter Email:");
            String status = getStringInput("Enter Passenger Status (ACTIVE/INACTIVE):");

            Passenger newPassenger = passengerService.createNewPassenger(passengerId, passengerName, phone, email, status);
            System.out.println("Passenger added successfully: " + newPassenger);
        } catch (InvalidPassengerIDException e) {
            System.out.println("Error adding passenger: " + e.getMessage());
        }
    }
    private void displayPassengerDetails() {
        String passengerId = getStringInput("Enter Passenger ID to display details:");
        Optional.ofNullable(passengerService.getPassengerList().get(passengerId))
                .ifPresentOrElse(
                        passengerService::displayPassengerDetails,
                        () -> System.out.println("Passenger not found.")
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
}
