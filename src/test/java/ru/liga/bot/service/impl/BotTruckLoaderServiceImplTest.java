package ru.liga.bot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.cargo.mapper.CargoTypeMapper;
import ru.liga.cargo.service.CargoTypeService;
import ru.liga.truck.mapper.TruckTypeMapper;
import ru.liga.truck.service.TruckTypeService;

@ExtendWith(MockitoExtension.class)
public class BotTruckLoaderServiceImplTest {

    @Mock
    private CargoTypeMapper cargoTypeMapper;

    @Mock
    private TruckTypeService truckTypeService;

    @Mock
    private CargoTypeService cargoTypeService;

    @Mock
    private TruckTypeMapper truckTypeMapper;

    @InjectMocks
    private BotTruckLoaderServiceImpl botTruckLoaderService;

    @Test
    public void createLoadedTrucks_ValidInput_ReturnsListOfTrucks() {
//        // Arrange
//        LoaderBotDto loaderBotDto = new LoaderBotDto(/* input values */);
//        TruckType truckType = new TruckType(/* truck type details */);
//        List<CargoType> cargoTypes = Arrays.asList(/* cargo types */);
//        List<Cargo> cargos = Arrays.asList(/* cargos */);
//        List<Truck> expectedTrucks = Arrays.asList(/* expected trucks */);
//
//        when(cargoTypeService.findByNameIn(anyList())).thenReturn(cargoTypes);
//        when(cargoTypeMapper.cargoTypeToCargo(any())).thenReturn(new Cargo(/* cargo details */));
//        when(truckTypeService.findByName(anyString())).thenReturn(truckType);
//        when(truckTypeMapper.toEntity(any())).thenReturn(truckType);
//
//        // Act
//        List<Truck> result = botTruckLoaderService.createLoadedTrucks(loaderBotDto);
//
//        // Assert
//        assertThat(result).isEqualTo(expectedTrucks);
//        // Additional assertions if needed
    }

    @Test
    public void createLoadedTrucks_InvalidCargoType_ReturnsEmptyList() {
//        LoaderBotDto loaderBotDto = new LoaderBotDto(/* input values */);
//        List<CargoType> cargoTypes = Arrays.asList();
//        when(cargoTypeService.findByNameIn(anyList())).thenReturn(cargoTypes);
//
//        List<Truck> result = botTruckLoaderService.createLoadedTrucks(loaderBotDto);
//
//        assertThat(result).isEmpty();
    }
}