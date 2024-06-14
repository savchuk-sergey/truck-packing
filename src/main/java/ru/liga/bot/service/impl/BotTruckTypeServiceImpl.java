package ru.liga.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.mapper.TruckTypeBotMapper;
import ru.liga.bot.service.BotTruckTypeService;
import ru.liga.truck.service.TruckTypeService;

import java.util.List;

@Component
public class BotTruckTypeServiceImpl implements BotTruckTypeService {
    private final TruckTypeService truckTypeService;
    private final TruckTypeBotMapper truckTypeBotMapper;

    @Autowired
    public BotTruckTypeServiceImpl(TruckTypeService truckTypeService, TruckTypeBotMapper truckTypeBotMapper) {
        this.truckTypeService = truckTypeService;
        this.truckTypeBotMapper = truckTypeBotMapper;
    }

    public void save(TruckTypeBotDto truckTypeBotDto) {
        truckTypeService.save(truckTypeBotMapper.toDto(truckTypeBotDto));
    }

    public void update(TruckTypeBotDto truckTypeBotDto) {
        truckTypeService.update(truckTypeBotDto.getId(), truckTypeBotMapper.toDto(truckTypeBotDto));
    }

    public void deleteById(Long id) {
        truckTypeService.deleteById(id);
    }

    public List<TruckTypeBotDto> findAll() {
        return truckTypeService.findAll().stream()
                .map(truckTypeBotMapper::toBotDto)
                .toList();
    }
}
