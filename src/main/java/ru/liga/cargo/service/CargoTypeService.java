package ru.liga.cargo.service;

import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;

import java.util.List;

public interface CargoTypeService {
    List<CargoTypeDto> findAll();

    List<CargoType> findByNameIn(List<String> names);

    CargoTypeDto findById(Long id);

    CargoTypeDto save(CargoTypeDto cargoType);

    void deleteById(Long id);

    CargoTypeDto update(Long id, CargoTypeDto cargoTypeDto);
}
