package com.omkcodes.cab_booking.model;

import com.omkcodes.cab_booking.enums.VehicleStatus;

public class Vehicle {
    private String vehicleId;
    private String model;
    private String registrationNumber;
    private String color;
    private boolean available;
    private int seatCapacity;
    private double perKmRate;

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", color='" + color + '\'' +
                ", available=" + available +
                ", seatCapacity=" + seatCapacity +
                ", perKmRate=" + perKmRate +
                '}';
    }



    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(double perKmRate) {
        this.perKmRate = perKmRate;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return null;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
    }
}
