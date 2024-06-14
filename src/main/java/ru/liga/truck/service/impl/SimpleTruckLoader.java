package ru.liga.truck.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.cargo.entity.Cargo;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.exception.TruckNumberExceededException;
import ru.liga.truck.service.TruckLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса TruckLoader, представляющая базовый загрузчик грузов на грузовик.
 */
@Component
@Slf4j
public class SimpleTruckLoader implements TruckLoader {
    /**
     * Размещает груз в грузовике.
     *
     * @param cargos груз, который размещается на грузовике
     */
    @Override
    public List<Truck> createLoadedTrucks(List<Cargo> cargos, int height, int width, int maxTruckNumber) throws TruckNumberExceededException {
        log.debug("placeCargo - cargo: {}", cargos);
        List<Truck> trucks = new ArrayList<>();

        for (Cargo cargo : cargos) {
            if (maxTruckNumber > 0 && trucks.size() >= maxTruckNumber) {
                throw new TruckNumberExceededException(maxTruckNumber, trucks.size());
            }

            log.trace("cargo: {}", cargo);

            List<List<Character>> size = cargo.getSize();
            Truck truck = new Truck(height, width);

            for (int i = 0; i < size.size(); i++) {
                for (int j = 0; j < size.get(i).size(); j++) {
                    int verticalReversed = truck.getHeight() - size.size() + i;
                    truck.occupyPlace(verticalReversed, j, size.get(i).get(j));
                }
            }
            trucks.add(truck);
        }
        return trucks;
    }
}
