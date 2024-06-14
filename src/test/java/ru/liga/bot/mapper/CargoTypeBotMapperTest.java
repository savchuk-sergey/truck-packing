package ru.liga.bot.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.cargo.entity.dto.CargoTypeDto;

import static org.assertj.core.api.Assertions.assertThat;

public class CargoTypeBotMapperTest {

    private final CargoTypeBotMapper mapper = Mappers.getMapper(CargoTypeBotMapper.class);

    @Test
    public void toBotDto_ShouldMapCargoTypeDtoToCargoTypeBotDto() {
        CargoTypeDto cargoTypeDto = new CargoTypeDto(1L, "TEST", "TEST");
        CargoTypeBotDto cargoTypeBotDto = mapper.toBotDto(cargoTypeDto);

        assertThat(cargoTypeBotDto).isNotNull();
        assertThat(cargoTypeDto.getName()).isEqualTo(cargoTypeBotDto.getName());
        assertThat(cargoTypeDto.getRepresentation()).isEqualTo(cargoTypeBotDto.getRepresentation());
        assertThat(cargoTypeDto.getId()).isEqualTo(cargoTypeBotDto.getId());
    }

    @Test
    public void toDto_ShouldMapCargoTypeBotDtoToCargoTypeDto() {
        CargoTypeBotDto cargoTypeBotDto = new CargoTypeBotDto(1L, "TEST", "TEST");
        CargoTypeDto cargoTypeDto = mapper.toDto(cargoTypeBotDto);

        assertThat(cargoTypeDto).isNotNull();
    }
}