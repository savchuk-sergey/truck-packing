package ru.liga.truck.mapper;

import org.mapstruct.Mapper;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.entity.dto.TruckTypeDto;

@Mapper(componentModel = "spring")
public interface TruckTypeMapper {

    TruckTypeDto toDto(TruckType truckType);

    TruckType toEntity(TruckTypeDto truckTypeDto);
}
