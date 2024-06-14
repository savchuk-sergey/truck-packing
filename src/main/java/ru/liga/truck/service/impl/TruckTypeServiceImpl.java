package ru.liga.truck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.entity.dto.TruckTypeDto;
import ru.liga.truck.exception.TruckTypeNotFoundException;
import ru.liga.truck.mapper.TruckTypeMapper;
import ru.liga.truck.repository.TruckTypeRepository;
import ru.liga.truck.service.TruckTypeService;

import java.util.List;

@Component
public class TruckTypeServiceImpl implements TruckTypeService {
    private final TruckTypeRepository truckTypeRepository;
    private final TruckTypeMapper truckTypeMapper;

    @Autowired
    public TruckTypeServiceImpl(TruckTypeRepository truckTypeRepository, TruckTypeMapper truckTypeMapper) {
        this.truckTypeRepository = truckTypeRepository;
        this.truckTypeMapper = truckTypeMapper;
    }

    public List<TruckTypeDto> findAll() {
        return ((List<TruckType>) truckTypeRepository.findAll()).stream()
                .map(truckTypeMapper::toDto)
                .toList();
    }

    public TruckTypeDto findByName(String name) {
        return truckTypeMapper.toDto(truckTypeRepository.findByName(name));
    }

    public TruckTypeDto findById(Long id) {
        return truckTypeMapper.toDto(
                truckTypeRepository
                        .findById(id)
                        .orElseThrow(() -> new TruckTypeNotFoundException(id))
        );
    }

    public TruckTypeDto save(TruckTypeDto truckType) {
        return truckTypeMapper.toDto(truckTypeRepository.save(truckTypeMapper.toEntity(truckType)));
    }

    public TruckTypeDto update(Long id, TruckTypeDto updatedTruckTypeDto) {
        TruckType truckTypeFromDb = truckTypeRepository.findById(id)
                .orElseThrow(() -> new TruckTypeNotFoundException(id));

        if (!truckTypeFromDb.getName().equals(updatedTruckTypeDto.getName())) {
            truckTypeFromDb.setName(updatedTruckTypeDto.getName());
        }
        if (truckTypeFromDb.getWidth() != updatedTruckTypeDto.getWidth()) {
            truckTypeFromDb.setWidth(updatedTruckTypeDto.getWidth());
        }
        if (truckTypeFromDb.getHeight() != updatedTruckTypeDto.getHeight()) {
            truckTypeFromDb.setHeight(updatedTruckTypeDto.getHeight());
        }
        return truckTypeMapper.toDto(truckTypeRepository.save(truckTypeFromDb));
    }

    public void deleteById(Long id) {
        truckTypeRepository.deleteById(id);
    }
}
