package ru.liga.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.mapper.CargoTypeBotMapper;
import ru.liga.bot.service.BotCargoTypeService;
import ru.liga.cargo.service.CargoTypeService;

import java.util.List;

@Component
public class BotCargoTypeServiceImpl implements BotCargoTypeService {
    private final CargoTypeService cargoTypeService;
    private final CargoTypeBotMapper cargoTypeBotMapper;

    @Autowired
    public BotCargoTypeServiceImpl(CargoTypeService cargoTypeService, CargoTypeBotMapper cargoTypeBotMapper) {
        this.cargoTypeService = cargoTypeService;
        this.cargoTypeBotMapper = cargoTypeBotMapper;
    }

    public void save(CargoTypeBotDto cargoTypeBotDto) {
        cargoTypeService.save(cargoTypeBotMapper.toDto(cargoTypeBotDto));
    }

    public void update(CargoTypeBotDto cargoTypeBotDto) {
        cargoTypeService.update(cargoTypeBotDto.getId(), cargoTypeBotMapper.toDto(cargoTypeBotDto));
    }

    public void deleteById(Long id) {
        cargoTypeService.deleteById(id);
    }

    public List<CargoTypeBotDto> findAll() {
        return cargoTypeService.findAll().stream()
                .map(cargoTypeBotMapper::toBotDto)
                .toList();
    }
}
