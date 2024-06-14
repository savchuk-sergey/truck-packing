package ru.liga.truck.validator;

import org.junit.jupiter.api.Test;
import ru.liga.truck.exception.InvalidTruckParameterException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TruckValidatorTest {

    @Test
    void validateParameters_ValidParameters_NoExceptionThrown() {
        List<List<Character>> fullness = Collections.singletonList(Collections.singletonList('A'));
        int height = 1;
        int width = 1;

        TruckValidator.validateParameters(fullness, height, width);
    }

    @Test
    void validateParameters_NegativeHeight_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = Collections.singletonList(Collections.singletonList('A'));
        int height = -1;
        int width = 1;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение высоты: -1. Высота должна быть больше нуля.");
    }

    @Test
    void validateParameters_ZeroHeight_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = Collections.singletonList(Collections.singletonList('A'));
        int height = 0;
        int width = 1;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение высоты: 0. Высота должна быть больше нуля.");
    }

    @Test
    void validateParameters_NegativeWidth_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = Collections.singletonList(Collections.singletonList('A'));
        int height = 1;
        int width = -1;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение ширины: -1. Ширина должна быть больше нуля.");
    }

    @Test
    void validateParameters_ZeroWidth_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = Collections.singletonList(Collections.singletonList('A'));
        int height = 1;
        int width = 0;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение ширины: 0. Ширина должна быть больше нуля.");
    }

    @Test
    void validateParameters_NullFullness_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = null;
        int height = 1;
        int width = 1;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение заполненности кузова: null. Заполненность не может быть пустой.");
    }

    @Test
    void validateParameters_MultipleInvalidParameters_ThrowsInvalidTruckParameterException() {
        List<List<Character>> fullness = null;
        int height = -1;
        int width = 0;

        assertThatThrownBy(() -> TruckValidator.validateParameters(fullness, height, width))
                .isInstanceOf(InvalidTruckParameterException.class)
                .hasMessageContaining("Неверное значение высоты: -1. Высота должна быть больше нуля.\n")
                .hasMessageContaining("Неверное значение ширины: 0. Ширина должна быть больше нуля.\n")
                .hasMessageContaining("Неверное значение заполненности кузова: null. Заполненность не может быть пустой.");
    }
}