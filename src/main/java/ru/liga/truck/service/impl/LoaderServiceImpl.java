package ru.liga.truck.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.service.CargoReader;
import ru.liga.constants.Constants;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.service.LoaderService;
import ru.liga.truck.type.TruckLoaderType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoaderServiceImpl implements LoaderService {
    private final CargoReader stringCargoReader;

    public List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos, int maxTruckNumber, TruckLoaderType truckLoaderType) {
        List<Cargo> cargos = stringCargoReader.read(rawCargos);
        List<Truck> trucks = new ArrayList<>(
                truckLoaderType.getTruckLoader()
                        .createLoadedTrucks(
                                cargos,
                                Constants.Truck.HEIGHT,
                                Constants.Truck.WIDTH,
                                maxTruckNumber
                        )
        );

        log.info("Данные грузов обработаны. Количество груза: {}, количество машин: {}", cargos.size(), trucks.size());
        log.debug("trucks {}", trucks);

        return trucks;
    }

    public List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos, int maxTruckNumber) {
        return getLoadedTrucksListFromTxtFile(rawCargos, maxTruckNumber, TruckLoaderType.SIMPLE);
    }

    public List<Truck> getLoadedTrucksListFromTxtFile(String rawCargos) {
        return getLoadedTrucksListFromTxtFile(rawCargos, Constants.Truck.INFINITE_MAX_COUNT, TruckLoaderType.SIMPLE);
    }

    public String getLoadedTrucksStringFromTxtFile(String rawCargos, int maxTruckNumber, TruckLoaderType truckLoaderType) {
        return getLoadedTrucksListFromTxtFile(rawCargos, maxTruckNumber, truckLoaderType).stream()
                .map(Truck::toString)
                .collect(Collectors.joining("\n\n"));
    }

    public String getLoadedTrucksStringFromTxtFile(String rawCargos, int maxTruckNumber) {
        return getLoadedTrucksStringFromTxtFile(rawCargos, maxTruckNumber, TruckLoaderType.SIMPLE);
    }

    public String getLoadedTrucksStringFromTxtFile(String rawCargos, TruckLoaderType truckLoaderType) {
        return getLoadedTrucksStringFromTxtFile(rawCargos, Constants.Truck.INFINITE_MAX_COUNT, truckLoaderType);
    }
}
