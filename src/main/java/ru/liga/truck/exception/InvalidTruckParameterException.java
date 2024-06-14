package ru.liga.truck.exception;

public class InvalidTruckParameterException extends RuntimeException {
    public InvalidTruckParameterException(String message) {
        super(message);
    }
}