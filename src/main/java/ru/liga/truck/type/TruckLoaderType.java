package ru.liga.truck.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.truck.service.TruckLoader;
import ru.liga.truck.service.impl.SimpleTruckLoader;
import ru.liga.truck.service.impl.SortTruckLoader;

@Getter
@AllArgsConstructor
public enum TruckLoaderType {
    SIMPLE(new SimpleTruckLoader(), "ПРОСТОЙ"),
    SORT(new SortTruckLoader(), "СОРТИРОВКА");

    private final TruckLoader truckLoader;
    private final String name;

    public static TruckLoader findByTypeName(String typeName) {
        for (TruckLoaderType loaderType : TruckLoaderType.values()) {
            if (loaderType.getName().equalsIgnoreCase(typeName)) {
                return loaderType.truckLoader;
            }
        }
        throw new IllegalArgumentException("Unknown truck loader type: " + typeName);
    }
}
