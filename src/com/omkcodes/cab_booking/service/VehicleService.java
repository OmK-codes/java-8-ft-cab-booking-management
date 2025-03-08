package com.omkcodes.cab_booking.service;
import com.omkcodes.cab_booking.enums.VehicleStatus;
import com.omkcodes.cab_booking.exception.InvalidVehicleIDException;
import com.omkcodes.cab_booking.model.Vehicle;
import java.util.HashMap;
import java.util.Optional;

public class VehicleService {
    private final HashMap<String, Vehicle> vehicleList = new HashMap<>();
    public void displayVehicleDetails(Vehicle vehicle) {
        Optional.ofNullable(vehicle)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Vehicle details are not available.")
                );
    }
    public Vehicle createNewVehicle(String vehicleId, String vehicleModel, String registrationNumber,
                                    String vehicleColor, boolean availability, int seatCapacity,
                                    double perKmRate, String statusInput) throws InvalidVehicleIDException {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            throw new InvalidVehicleIDException("Vehicle ID cannot be null or empty.");
        }
        return vehicleList.computeIfAbsent(vehicleId, id -> {
            Vehicle vehicle = new Vehicle();
            VehicleStatus vehicleStatus = VehicleStatus.AVAILABLE;
            try {
                vehicleStatus = VehicleStatus.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid vehicle status! Setting default to AVAILABLE.");
            }
            vehicle.setVehicleId(id);
            vehicle.setModel(vehicleModel);
            vehicle.setRegistrationNumber(registrationNumber);
            vehicle.setColor(vehicleColor);
            vehicle.setAvailable(availability);
            vehicle.setSeatCapacity(seatCapacity);
            vehicle.setPerKmRate(perKmRate);
            vehicle.setVehicleStatus(vehicleStatus);
            return vehicle;
        });
    }
    public void showAllVehicles() {
        if (vehicleList.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            vehicleList.forEach((id, vehicle) -> System.out.println("Vehicle Information: " + vehicle));
        }
    }
    public HashMap<String, Vehicle> getVehicleList() {
        return vehicleList;
    }
}