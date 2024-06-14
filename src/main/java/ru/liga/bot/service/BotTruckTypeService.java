package ru.liga.bot.service;

import ru.liga.bot.entity.dto.TruckTypeBotDto;

import java.util.List;

public interface BotTruckTypeService {
    void save(TruckTypeBotDto truckTypeBotDto);

    void update(TruckTypeBotDto truckTypeBotDto);

    void deleteById(Long id);

    List<TruckTypeBotDto> findAll();
}
