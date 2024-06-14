package ru.liga.truck.exception;

public class TruckNumberExceededException extends RuntimeException {

    public TruckNumberExceededException(int maxTruckNumber, int currentNumber) {
        super("The maximum truck number has been exceeded. Max number is: " + maxTruckNumber + ", current number: " + currentNumber);
    }

}