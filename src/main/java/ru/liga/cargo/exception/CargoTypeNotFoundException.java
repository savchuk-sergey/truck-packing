package ru.liga.cargo.exception;

public class CargoTypeNotFoundException extends RuntimeException {
    public CargoTypeNotFoundException(Long id) {
        super("Cargo type with ID " + id + " not found");
    }
}
