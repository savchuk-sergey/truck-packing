package ru.liga.bot.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.truck.entity.dto.TruckTypeDto;

import static org.assertj.core.api.Assertions.assertThat;

public class TruckTypeBotMapperTest {

    private final TruckTypeBotMapper mapper = Mappers.getMapper(TruckTypeBotMapper.class);

    @Test
    public void toBotDto_ShouldMapTruckTypeDtoToTruckTypeBotDto() {
        TruckTypeDto truckTypeDto = new TruckTypeDto(1L, "TEST", 1, 1);
        TruckTypeBotDto truckTypeBotDto = mapper.toBotDto(truckTypeDto);

        assertThat(truckTypeBotDto).isNotNull();
    }

    @Test
    public void toDto_ShouldMapTruckTypeBotDtoToTruckTypeDto() {
        TruckTypeBotDto truckTypeBotDto = new TruckTypeBotDto(1L, "TEST", 1, 1);
        TruckTypeDto truckTypeDto = mapper.toDto(truckTypeBotDto);

        assertThat(truckTypeDto).isNotNull();
    }
}