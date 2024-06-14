package ru.liga.bot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private CargoTypeBotDto cargoTypeDto1;

    @Mock
    private CargoTypeBotDto cargoTypeDto2;

    @Mock
    private TruckTypeBotDto truckTypeDto1;

    @Mock
    private TruckTypeBotDto truckTypeDto2;

    @Test
    public void getMarkdownTableStringFromCargoTypeDtos_ValidInput_ReturnsMarkdownTableString() {
        List<CargoTypeBotDto> cargoTypeDtos = Arrays.asList(cargoTypeDto1, cargoTypeDto2);
        when(cargoTypeDto1.getId()).thenReturn(1L);
        when(cargoTypeDto1.getName()).thenReturn("Cargo1");
        when(cargoTypeDto1.getRepresentation()).thenReturn("Representation1");
        when(cargoTypeDto2.getId()).thenReturn(2L);
        when(cargoTypeDto2.getName()).thenReturn("Cargo2");
        when(cargoTypeDto2.getRepresentation()).thenReturn("Representation2");

        String result = messageService.getMarkdownTableStringFromCargoTypeDtos(cargoTypeDtos);

        assertThat(result).contains("1", "Cargo1", "Representation1", "2", "Cargo2", "Representation2");
    }

    @Test
    public void getMarkdownTableStringFromTruckTypeDtos_ValidInput_ReturnsMarkdownTableString() {
        // Arrange
        List<TruckTypeBotDto> truckTypeDtos = Arrays.asList(truckTypeDto1, truckTypeDto2);
        when(truckTypeDto1.getId()).thenReturn(1L);
        when(truckTypeDto1.getName()).thenReturn("Truck1");
        when(truckTypeDto1.getWidth()).thenReturn(10);
        when(truckTypeDto1.getHeight()).thenReturn(5);
        when(truckTypeDto2.getId()).thenReturn(2L);
        when(truckTypeDto2.getName()).thenReturn("Truck2");
        when(truckTypeDto2.getWidth()).thenReturn(8);
        when(truckTypeDto2.getHeight()).thenReturn(4);

        String result = messageService.getMarkdownTableStringFromTruckTypeDtos(truckTypeDtos);

        assertThat(result).contains("1", "Truck1", "10", "5", "2", "Truck2", "8", "4");
    }
}