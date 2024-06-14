package ru.liga.truck.validator;

import ru.liga.truck.exception.InvalidTruckParameterException;

import java.util.List;

public class TruckValidator {
    public static void validateParameters(List<List<Character>> fullness, int height, int width) {
        StringBuilder errorMessage = new StringBuilder();

        if (height <= 0) {
            errorMessage.append("Неверное значение высоты: ").append(height).append(". Высота должна быть больше нуля.\n");
        }
        if (width <= 0) {
            errorMessage.append("Неверное значение ширины: ").append(width).append(". Ширина должна быть больше нуля.\n");
        }
        if (fullness == null) {
            errorMessage.append("Неверное значение заполненности кузова: ").append(fullness).append(". Заполненность не может быть пустой.");
        }

        if (!errorMessage.isEmpty()) {
            throw new InvalidTruckParameterException(errorMessage.toString());
        }
    }
}
