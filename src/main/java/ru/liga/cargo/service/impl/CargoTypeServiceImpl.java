package ru.liga.cargo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;
import ru.liga.cargo.exception.CargoTypeNotFoundException;
import ru.liga.cargo.mapper.CargoTypeMapper;
import ru.liga.cargo.repository.CargoTypeRepository;
import ru.liga.cargo.service.CargoTypeService;

import java.util.List;

@Component
public class CargoTypeServiceImpl implements CargoTypeService {
    private final CargoTypeRepository cargoTypeRepository;
    private final CargoTypeMapper cargoTypeMapper;

    @Autowired
    public CargoTypeServiceImpl(CargoTypeRepository cargoTypeRepository, CargoTypeMapper cargoTypeMapper) {
        this.cargoTypeRepository = cargoTypeRepository;
        this.cargoTypeMapper = cargoTypeMapper;
    }

    public List<CargoTypeDto> findAll() {
        return ((List<CargoType>) cargoTypeRepository.findAll()).stream()
                .map(cargoTypeMapper::toDto)
                .toList();
    }

    public List<CargoType> findByNameIn(List<String> names) {
        return cargoTypeRepository.findByNameIn(names);
    }

    public CargoTypeDto findById(Long id) {
        return cargoTypeMapper.toDto(
                cargoTypeRepository
                        .findById(id)
                        .orElseThrow(() -> new CargoTypeNotFoundException(id))
        );
    }

    public CargoTypeDto save(CargoTypeDto cargoType) {
        return cargoTypeMapper.toDto(cargoTypeRepository.save(cargoTypeMapper.toEntity(cargoType)));
    }

    public CargoTypeDto update(Long id, CargoTypeDto updatedCargoTypeDto) {
        CargoType cargoTypeFromDb = cargoTypeRepository.findById(id)
                .orElseThrow(() -> new CargoTypeNotFoundException(id));

        if (!cargoTypeFromDb.getName().equals(updatedCargoTypeDto.getName())) {
            cargoTypeFromDb.setName(updatedCargoTypeDto.getName());
        }
        if (updatedCargoTypeDto.getRepresentation() != null && !updatedCargoTypeDto.getRepresentation().isEmpty()) {
            cargoTypeFromDb.setRepresentation(updatedCargoTypeDto.getRepresentation());
        }
        return cargoTypeMapper.toDto(cargoTypeRepository.save(cargoTypeFromDb));
    }

    public void deleteById(Long id) {
        cargoTypeRepository.deleteById(id);
    }
}
