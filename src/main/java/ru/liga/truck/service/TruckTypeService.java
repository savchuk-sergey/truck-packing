package ru.liga.truck.service;

import ru.liga.truck.entity.dto.TruckTypeDto;

import java.util.List;

public interface TruckTypeService {
    List<TruckTypeDto> findAll();

    TruckTypeDto findByName(String name);

    TruckTypeDto findById(Long id);

    TruckTypeDto save(TruckTypeDto truckTypeDto);

    void deleteById(Long id);

    TruckTypeDto update(Long id, TruckTypeDto truckTypeDto);
}
