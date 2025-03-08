package com.omkcodes.cab_booking.service;
import com.omkcodes.cab_booking.enums.DriverStatus;
import com.omkcodes.cab_booking.exception.InvalidDriverIDException;
import com.omkcodes.cab_booking.model.Driver;
import java.util.*;
import java.util.stream.Collectors;

public class DriverService {
    private final HashMap<String, Driver> driverList = new HashMap<>();
    public void displayDriverDetails(Driver driver) {
        Optional.ofNullable(driver)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Driver details are not available."));
    }
    public Driver createNewDriver(String driverId, String driverName, String phone,
                                  String licenseNumber, int totalTrips, boolean onlineStatus,
                                  String statusInput) throws InvalidDriverIDException {
        if (driverId == null || driverId.trim().isEmpty()) {
            throw new InvalidDriverIDException("Driver ID cannot be null or empty.");
        }
        return driverList.computeIfAbsent(driverId, id -> {
            Driver driver = new Driver();
            DriverStatus driverStatus = DriverStatus.INACTIVE;
            try {
                driverStatus = DriverStatus.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid driver status! Setting default to INACTIVE.");
            }
            driver.setDriverId(id);
            driver.setDriverName(driverName);
            driver.setPhone(phone);
            driver.setLicenseNumber(licenseNumber);
            driver.setTotalTrips(totalTrips);
            driver.setOnlineStatus(onlineStatus);
            driver.setDriverStatus(driverStatus);
            return driver;
        });
    }
    public void showAllDrivers() {
        if (driverList.isEmpty()) {
            System.out.println("No drivers available.");
        } else {
            driverList.values().forEach(driver -> System.out.println("Driver Information: " + driver));
        }
    }
    public List<Driver> getOnlineDrivers() {
        if (driverList.isEmpty()) {
            System.out.println("No drivers available.");
            return Collections.emptyList();
        }
        return driverList.values().stream()
                .filter(Driver::isOnlineStatus)
                .collect(Collectors.toList());
    }
    public List<Driver> getTopDriversByTrips(int limit) {
        if (driverList.isEmpty()) {
            System.out.println("No drivers available.");
            return Collections.emptyList();
        }
        return driverList.values().stream()
                .sorted(Comparator.comparingInt(Driver::getTotalTrips).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
    public HashMap<String, Driver> getDriverList() {
        return driverList;
    }
}