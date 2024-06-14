package ru.liga.bot.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.mapper.CargoTypeBotMapper;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;
import ru.liga.cargo.service.CargoTypeService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BotCargoTypeServiceImplTest {
    @Mock
    private CargoTypeService cargoTypeService;

    @Mock
    private CargoTypeBotMapper cargoTypeBotMapper;

    @InjectMocks
    private BotCargoTypeServiceImpl botCargoTypeService;

    private CargoTypeBotDto cargoTypeBotDto;
    private CargoTypeDto cargoTypeDto;
    private CargoType cargoType;

    @BeforeEach
    void setUp() {
        cargoTypeBotDto = new CargoTypeBotDto(1L, "Electronics", "E\\nL\\nC");
        cargoTypeDto = new CargoTypeDto(1L, "Electronics", "E\\nL\\nC");
        cargoType = new CargoType(1L, "Electronics", "E\\nL\\nC");
    }

    @Test
    void save_ValidBotDto_CallsCargoTypeServiceSave() {
        when(cargoTypeBotMapper.toDto(cargoTypeBotDto)).thenReturn(cargoTypeDto);

        botCargoTypeService.save(cargoTypeBotDto);

        verify(cargoTypeService, times(1)).save(cargoTypeDto);
    }

    @Test
    void update_ValidBotDto_CallsCargoTypeServiceUpdate() {
        when(cargoTypeBotMapper.toDto(cargoTypeBotDto)).thenReturn(cargoTypeDto);

        botCargoTypeService.update(cargoTypeBotDto);

        verify(cargoTypeService, times(1)).update(cargoTypeBotDto.getId(), cargoTypeDto);
    }

    @Test
    void deleteById_ValidId_CallsCargoTypeServiceDeleteById() {
        Long id = 1L;

        botCargoTypeService.deleteById(id);

        verify(cargoTypeService, times(1)).deleteById(id);
    }

    @Test
    void findAll_WhenCalled_ReturnsListOfBotDtos() {
        List<CargoTypeDto> cargoTypeDtoList = Collections.singletonList(cargoTypeDto);
        when(cargoTypeService.findAll()).thenReturn(cargoTypeDtoList);
        when(cargoTypeBotMapper.toBotDto(cargoTypeDto)).thenReturn(cargoTypeBotDto);

        List<CargoTypeBotDto> result = botCargoTypeService.findAll();

        assertThat(result).containsExactly(cargoTypeBotDto);
        verify(cargoTypeService, times(1)).findAll();
        verify(cargoTypeBotMapper, times(1)).toBotDto(cargoTypeDto);
    }
}