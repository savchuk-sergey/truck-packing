package ru.liga.bot.mapper;

import org.mapstruct.Mapper;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.truck.entity.dto.TruckTypeDto;

@Mapper(componentModel = "spring")
public interface TruckTypeBotMapper {
    TruckTypeBotDto toBotDto(TruckTypeDto truckTypeDto);

    TruckTypeDto toDto(TruckTypeBotDto truckTypeBotDto);
}
