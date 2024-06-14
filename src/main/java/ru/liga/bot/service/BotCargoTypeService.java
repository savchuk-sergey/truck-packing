package ru.liga.bot.service;

import ru.liga.bot.entity.dto.CargoTypeBotDto;

import java.util.List;

public interface BotCargoTypeService {

    void save(CargoTypeBotDto cargoTypeBotDto);

    void update(CargoTypeBotDto cargoTypeBotDto);

    void deleteById(Long id);

    List<CargoTypeBotDto> findAll();
}
