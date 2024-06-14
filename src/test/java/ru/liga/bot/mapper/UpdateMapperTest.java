package ru.liga.bot.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateMapperTest {
    private UpdateMapper updateMapper;

    @BeforeEach
    public void setUp() {
        updateMapper = new UpdateMapper();
    }

    @Test
    public void getCargoTypeBotDtoFromUpdateMessage_ValidInput_ReturnsCargoTypeBotDto() {
        // Arrange
        String updateText = "-и \"123\" -н \"Cargo Name\" -п \"Representation\"";

        // Act
        CargoTypeBotDto result = updateMapper.getCargoTypeBotDtoFromUpdateMessage(updateText);

        // Assert
        assertThat(result.getId()).isEqualTo(123L);
        assertThat(result.getName()).isEqualTo("Cargo Name");
        assertThat(result.getRepresentation()).isEqualTo("Representation");
    }

    @Test
    public void getTruckTypeBotDtoFromUpdateMessage_ValidInput_ReturnsTruckTypeBotDto() {
        String updateText = "-и \"456\" -н \"Truck Name\" -в \"5\" -ш \"10\"";

        TruckTypeBotDto result = updateMapper.getTruckTypeBotDtoFromUpdateMessage(updateText);

        assertThat(result.getId()).isEqualTo(456L);
        assertThat(result.getName()).isEqualTo("Truck Name");
        assertThat(result.getHeight()).isEqualTo(5);
        assertThat(result.getWidth()).isEqualTo(10);
    }

    @Test
    public void getLoaderBotDtoFromUpdateMessage_ValidInput_ReturnsLoaderBotDto() {
        // Arrange
        String updateText = "-в \"Truck Name\" -м \"123\" -а \"Algorithm\" -г \"Cargo1,Cargo2\"";

        // Act
        LoaderBotDto result = updateMapper.getLoaderBotDtoFromUpdateMessage(updateText);

        // Assert
        assertThat(result.getTruckName()).isEqualTo("Truck Name");
        assertThat(result.getTruckNumber()).isEqualTo(123);
        assertThat(result.getAlgorithmType()).isEqualTo("Algorithm");
        assertThat(result.getCargoNames()).containsExactly("Cargo1", "Cargo2");
    }
}