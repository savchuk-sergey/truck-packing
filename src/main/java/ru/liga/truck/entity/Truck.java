package ru.liga.truck.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truck.validator.TruckValidator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class Truck {
    private final List<List<Character>> fullness;
    private final int height;
    private final int width;

    /**
     * Конструктор создает грузовик с заданными размерами и инициализирует пустые места.
     *
     * @param height высота грузовика
     * @param width  ширина грузовика
     * @throws IllegalArgumentException если высота или ширина меньше, или равна нулю
     */
    public Truck(int height, int width) {
        this(new ArrayList<>(), height, width);
    }

    public Truck(List<List<Character>> fullness, int height, int width) {
        log.debug("new Truck(fullness: {})", fullness);

        TruckValidator.validateParameters(fullness, height, width);

        if (fullness.isEmpty()) {
            for (int i = 0; i < height; i++) {
                fullness.add(new ArrayList<>());
            }
        }

        this.fullness = fullness;
        this.height = height;
        this.width = width;
    }

    /**
     * Возвращает количество свободного места в грузовике.
     *
     * @return количество свободного места
     */
    public int getFreeHeight() {
        long freeHeight = height - fullness.stream().filter(f -> !f.isEmpty()).count();
        log.trace("getFreeHeight: {}", freeHeight);
        return (int) freeHeight;
    }

    /**
     * Возвращает строковое представление грузовика с размещенными грузами.
     *
     * @return строковое представление грузовика
     */
    @Override
    public String toString() {
        log.trace("print");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < height; i++) {
            stringBuilder.append("+");
            for (int j = 0; j < width; j++) {
                if (j >= fullness.get(i).size() || fullness.get(i).get(j) == null) {
                    stringBuilder.append(" ");
                } else {
                    stringBuilder.append(fullness.get(i).get(j));
                }

            }
            stringBuilder.append("+\n");
        }
        stringBuilder.append("+").append("+".repeat(width)).append("+\n");
        return stringBuilder.toString();
    }

    /**
     * Занимает указанное место в грузовике заданным объемом груза.
     *
     * @param vertical    вертикальная координата места
     * @param horizontal  горизонтальная координата места
     * @param cargoVolume объем груза
     */
    public void occupyPlace(int vertical, int horizontal, int cargoVolume) {
        log.debug("occupyPlace - vertical: {}, horizontal: {}, cargoVolume: {}, height: {}", vertical, horizontal, cargoVolume, height);
        if (vertical >= height || horizontal >= width) {
            throw new IllegalArgumentException("Невозможно занять это место. Координата по вертикали: " + vertical + ", по горизонтали: " + horizontal);
        }

        if (fullness.size() <= vertical) {
            fullness.add(vertical, new ArrayList<>());
        }
        fullness.get(vertical).add(horizontal, (char) cargoVolume);
    }
}