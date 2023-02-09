package com.liverday;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingLot {
    private static final int DEFAULT_CAPACITY = 100;
    private final int capacity;
    private final List<ParkedVehicle> vehicles = new ArrayList<>();

    public ParkingLot() {
        this.capacity = DEFAULT_CAPACITY;
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public int availableSpaces() {
        return capacity - vehicles.size();
    }

    private Optional<ParkedVehicle> findVehicleByPlate(String plate) {
        return vehicles.stream()
                .filter(parkedVehicle -> parkedVehicle.vehicle().plate().equals(plate))
                .findFirst();
    }

    private void deleteVehicleByPlate(String plate) {
        vehicles.removeIf(
                parkedVehicle -> parkedVehicle.vehicle().plate().equals(plate)
        );
    }

    public List<ParkedVehicle> getParkedVehicles() {
        return vehicles;
    }

    public void checkIn(Vehicle vehicle, LocalDateTime checkInDate) {
        if (availableSpaces() <= 0) {
            throw new RuntimeException("The parking lot is full");
        }

        vehicles.add(new ParkedVehicle(vehicle, checkInDate));
    }

    public double checkOut(String plate, LocalDateTime checkOutDate, TicketCalculator calculator) {
        final ParkedVehicle vehicle = findVehicleByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        deleteVehicleByPlate(plate);

        final long differenceInHours = ChronoUnit.HOURS.between(vehicle.checkInDate(), checkOutDate);
        return calculator.calculate(differenceInHours);
    }
}
