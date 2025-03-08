package com.omkcodes.cab_booking.service;
import com.omkcodes.cab_booking.enums.BookingStatus;
import com.omkcodes.cab_booking.exception.InvalidBookingIDException;
import com.omkcodes.cab_booking.model.Booking;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {
    private final HashMap<String, Booking> bookingList = new HashMap<>();

    public void displayBookingDetails(Booking booking) {
        Optional.ofNullable(booking)
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("Booking details are not available."));
    }
    public Booking createNewBooking(String bookingId, String passengerId, String passengerName,
                                    String driverId, String driverName, String vehicleId,
                                    String pickupLocation, String dropLocation, double fare,
                                    double distance, String statusInput) throws InvalidBookingIDException {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new InvalidBookingIDException("Booking ID cannot be null or empty.");
        }
        return bookingList.computeIfAbsent(bookingId, id -> {
            Booking booking = new Booking();
            BookingStatus bookingStatus = BookingStatus.PENDING;
            try {
                bookingStatus = BookingStatus.valueOf(statusInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid booking status! Setting default to PENDING.");
            }

            booking.setBookingId(id);
            booking.setPassengerId(passengerId);
            booking.setPassengerName(passengerName);
            booking.setDriverId(driverId);
            booking.setDriverName(driverName);
            booking.setVehicleId(vehicleId);
            booking.setPickupLocation(pickupLocation);
            booking.setDropLocation(dropLocation);
            booking.setFare(fare);
            booking.setDistance(distance);
            booking.setBookingStatus(bookingStatus);
            return booking;
        });
    }
    public void showAllBookings() {
        if (bookingList.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            bookingList.values().forEach(booking -> System.out.println("Booking Information: " + booking));
        }
    }
    public List<String> getAllBookingIds() {
        return bookingList.values().stream()
                .map(Booking::getBookingId)
                .collect(Collectors.toList());
    }
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingList.values().stream()
                .filter(booking -> Objects.equals(booking.getBookingStatus(), status))
                .collect(Collectors.toList());
    }
    public HashMap<String, Booking> getBookingList() {
        return bookingList;
    }
}