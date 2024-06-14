package ru.liga.cargo.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CargoTypeMapperTest {
    private CargoTypeMapper cargoTypeMapper;

    @BeforeEach
    void setUp() {
        cargoTypeMapper = Mappers.getMapper(CargoTypeMapper.class);
    }

    @Test
    void toDto_ValidCargoType_ReturnsCargoTypeDto() {
        CargoType cargoType = new CargoType();
        cargoType.setId(1L);
        cargoType.setName("Electronics");
        cargoType.setRepresentation("E\\nL\\nC");

        CargoTypeDto result = cargoTypeMapper.toDto(cargoType);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Electronics");
        assertThat(result.getRepresentation()).isEqualTo("E\\nL\\nC");
    }

    @Test
    void toEntity_ValidCargoTypeDto_ReturnsCargoType() {
        CargoTypeDto cargoTypeDto = new CargoTypeDto();
        cargoTypeDto.setId(1L);
        cargoTypeDto.setName("Electronics");
        cargoTypeDto.setRepresentation("E\\nL\\nC");

        CargoType result = cargoTypeMapper.toEntity(cargoTypeDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Electronics");
        assertThat(result.getRepresentation()).isEqualTo("E\\nL\\nC");
    }

    @Test
    void botDtoToDto_ValidBotDto_ReturnsCargoTypeDto() {
        CargoTypeBotDto botDto = new CargoTypeBotDto();
        botDto.setId(1L);
        botDto.setName("Electronics");
        botDto.setRepresentation("E\\nL\\nC");

        CargoTypeDto result = cargoTypeMapper.botDtoToDto(botDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Electronics");
        assertThat(result.getRepresentation()).isEqualTo("E\\nL\\nC");
    }

    @Test
    void cargoTypeToCargo_ValidCargoType_ReturnsCargo() {
        CargoType cargoType = new CargoType();
        cargoType.setName("Electronics");
        cargoType.setRepresentation("E\\nL\\nC");

        Cargo result = cargoTypeMapper.cargoTypeToCargo(cargoType);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo('E');
        assertThat(result.getSize()).containsExactly(
                List.of('E'),
                List.of('L'),
                List.of('C')
        );
    }

    @Test
    void parseRepresentation_ValidRepresentation_ReturnsListOfListOfCharacters() {
        String representation = "E\\nL\\nC";

        List<List<Character>> result = cargoTypeMapper.parseRepresentation(representation);

        assertThat(result).containsExactly(
                List.of('E'),
                List.of('L'),
                List.of('C')
        );
    }
}