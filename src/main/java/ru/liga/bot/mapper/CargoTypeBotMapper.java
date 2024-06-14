package ru.liga.bot.mapper;

import org.mapstruct.Mapper;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.cargo.entity.dto.CargoTypeDto;

@Mapper(componentModel = "spring")
public interface CargoTypeBotMapper {
    CargoTypeBotDto toBotDto(CargoTypeDto cargoTypeDto);

    CargoTypeDto toDto(CargoTypeBotDto cargoTypeBotDto);
}
