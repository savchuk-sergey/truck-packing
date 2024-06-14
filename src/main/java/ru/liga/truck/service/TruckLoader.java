package ru.liga.truck.service;

import org.springframework.stereotype.Service;
import ru.liga.cargo.entity.Cargo;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.exception.TruckNumberExceededException;

import java.util.List;

@Service
public interface TruckLoader {
    List<Truck> createLoadedTrucks(List<Cargo> cargos, int height, int width, int maxTruckNumber) throws TruckNumberExceededException;
}
