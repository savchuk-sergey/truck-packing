package ru.liga.truck.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.cargo.entity.Cargo;
import ru.liga.constants.Constants;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.exception.TruckNumberExceededException;
import ru.liga.truck.service.TruckLoader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Реализация интерфейса TruckLoader, представляющая загрузчик грузов на грузовик с помощью сортировки.
 */
@Component
@Slf4j
public class SortTruckLoader implements TruckLoader {
    /**
     * Размещает груз в грузовиках.
     *
     * @param cargos груз, который размещается на грузовике
     */
    @Override
    public List<Truck> createLoadedTrucks(List<Cargo> cargos, int height, int width, int maxTruckNumber) throws TruckNumberExceededException {
        log.trace("placeCargo - cargo: {}", cargos);
        List<Truck> trucks = new ArrayList<>();
        List<Cargo> sortedCargos = cargos.stream()
                .sorted(Comparator.comparing(Cargo::getWidth))
                .toList();
        Truck truck = Truck.builder()
                .height(height)
                .width(width)
                .build();

        for (Cargo cargo : sortedCargos) {
            if (maxTruckNumber > 0 && trucks.size() > maxTruckNumber) {
                throw new TruckNumberExceededException(maxTruckNumber, trucks.size());
            }

            int truckFreeHeight = truck.getFreeHeight();
            List<List<Character>> size = cargo.getSize();

            if (truckFreeHeight < cargo.getHeight()) {
                trucks.add(truck);
                truck = Truck.builder()
                        .height(Constants.Truck.HEIGHT)
                        .width(Constants.Truck.WIDTH)
                        .build();
            }

            for (int i = 0; i < size.size(); i++) {
                for (int j = 0; j < size.get(i).size(); j++) {
                    int verticalReversed = truck.getHeight() - size.size() + i;
                    truck.occupyPlace(verticalReversed, j, size.get(i).get(j));
                }
            }
        }

        trucks.add(truck);

        return trucks;
    }
}
