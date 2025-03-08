package com.omkcodes.cab_booking.service;
import com.omkcodes.cab_booking.enums.PassengerStatus;
import com.omkcodes.cab_booking.exception.InvalidPassengerIDException;
import com.omkcodes.cab_booking.model.Passenger;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PassengerService {
    private final HashMap<String, Passenger> passengerList = new HashMap<>();
    public void displayPassengerDetails(Passenger passenger) {
        Optional.ofNullable(passenger)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Passenger details are not available."));
    }
    public List<Passenger> getPassengersByStatus(PassengerStatus status) {
        if (passengerList.isEmpty()) {
            System.out.println("No passengers available.");
            return Collections.emptyList();
        }
        return passengerList.values().stream()
                .filter(passenger -> Objects.equals(passenger.getPassengerStatus(), status))
                .collect(Collectors.toList());
    }
    public List<String> getAllPassengerNames() {
        if (passengerList.isEmpty()) {
            System.out.println("No passengers available.");
            return Collections.emptyList();
        }
        return passengerList.values().stream()
                .map(Passenger::getPassengerName)
                .collect(Collectors.toList());
    }
    public void showAllPassengers() {
        if (passengerList.isEmpty()) {
            System.out.println("No passengers available.");
        } else {
            passengerList.forEach((id, passenger) -> System.out.println("Passenger: " + passenger));
        }
    }
    public HashMap<String, Passenger> getPassengerList() {
        return passengerList;
    }
    public Passenger createNewPassenger(String passengerId, String passengerName, String phone, String email, String statusInput) throws InvalidPassengerIDException {
        if (passengerId == null || passengerId.trim().isEmpty()) {
            throw new InvalidPassengerIDException("Passenger ID cannot be null or empty.");
        }
        return passengerList.computeIfAbsent(passengerId, id -> {
            Passenger passenger = new Passenger();
            PassengerStatus passengerStatus = PassengerStatus.INACTIVE;
            try {
                passengerStatus = PassengerStatus.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid passenger status! Setting default to INACTIVE.");
            }
            passenger.setPassengerId(id);
            passenger.setPassengerName(passengerName);
            passenger.setPhone(phone);
            passenger.setEmail(email);
            passenger.setPassengerStatus(passengerStatus);
            return passenger;
        });
    }
}