package ru.liga.cargo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CargoTypeMapper {

    CargoTypeDto toDto(CargoType cargoType);

    CargoType toEntity(CargoTypeDto cargoTypeDto);

    default CargoTypeDto botDtoToDto(CargoTypeBotDto botDto) {
        return CargoTypeDto.builder()
                .id(botDto.getId())
                .name(botDto.getName())
                .representation(botDto.getRepresentation())
                .build();
    }

    @Mapping(target = "title", expression = "java(cargoType.getName().toUpperCase().charAt(0))")
    @Mapping(target = "size", expression = "java(parseRepresentation(cargoType.getRepresentation()))")
    Cargo cargoTypeToCargo(CargoType cargoType);

    default List<List<Character>> parseRepresentation(String representation) {
        return Arrays.stream(representation.split("\\\\n"))
                .map(line -> line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}