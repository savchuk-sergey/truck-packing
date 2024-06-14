package ru.liga.truck.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.entity.dto.TruckTypeDto;

import static org.assertj.core.api.Assertions.assertThat;

public class TruckTypeMapperTest {

    private TruckTypeMapper truckTypeMapper;

    @BeforeEach
    void setUp() {
        truckTypeMapper = Mappers.getMapper(TruckTypeMapper.class);
    }

    @Test
    void toDto_ValidTruckType_ReturnsTruckTypeDto() {
        TruckType truckType = new TruckType(1L, "Type1", 2, 3);

        TruckTypeDto truckTypeDto = truckTypeMapper.toDto(truckType);

        assertThat(truckTypeDto).isNotNull();
        assertThat(truckTypeDto.getId()).isEqualTo(1L);
        assertThat(truckTypeDto.getName()).isEqualTo("Type1");
        assertThat(truckTypeDto.getWidth()).isEqualTo(2);
        assertThat(truckTypeDto.getHeight()).isEqualTo(3);
    }

    @Test
    void toEntity_ValidTruckTypeDto_ReturnsTruckType() {
        TruckTypeDto truckTypeDto = new TruckTypeDto(1L, "Type1", 2, 3);

        TruckType truckType = truckTypeMapper.toEntity(truckTypeDto);

        assertThat(truckType).isNotNull();
        assertThat(truckType.getId()).isEqualTo(1L);
        assertThat(truckType.getName()).isEqualTo("Type1");
        assertThat(truckType.getWidth()).isEqualTo(2);
        assertThat(truckType.getHeight()).isEqualTo(3);
    }

    @Test
    void toDto_NullTruckType_ReturnsNull() {
        TruckTypeDto truckTypeDto = truckTypeMapper.toDto(null);

        assertThat(truckTypeDto).isNull();
    }

    @Test
    void toEntity_NullTruckTypeDto_ReturnsNull() {
        TruckType truckType = truckTypeMapper.toEntity(null);

        assertThat(truckType).isNull();
    }
}