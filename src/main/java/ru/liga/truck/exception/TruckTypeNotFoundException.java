package ru.liga.truck.exception;

public class TruckTypeNotFoundException extends RuntimeException {
    public TruckTypeNotFoundException(Long id) {
        super("Truck type with ID " + id + " not found");
    }
}
