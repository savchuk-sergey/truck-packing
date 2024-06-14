package ru.liga.truck.service;

import ru.liga.truck.entity.Truck;
import ru.liga.truck.type.TruckLoaderType;

import java.util.List;

public interface LoaderService {
    List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos, int maxTruckNumber, TruckLoaderType truckLoaderType);

    List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos, int maxTruckNumber);

    List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos);

    String getLoadedTrucksStringFromTxtFile(String rawCargos, int maxTruckNumber, TruckLoaderType truckLoaderType);

    String getLoadedTrucksStringFromTxtFile(String rawCargos, int maxTruckNumber);

    String getLoadedTrucksStringFromTxtFile(String rawCargos, TruckLoaderType truckLoaderType);
}
