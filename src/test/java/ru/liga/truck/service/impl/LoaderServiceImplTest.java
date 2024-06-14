package ru.liga.truck.service.impl;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.service.CargoReader;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.type.TruckLoaderType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoaderServiceImplTest {

    @Mock
    private CargoReader cargoReader;

    @InjectMocks
    private LoaderServiceImpl loaderService;

    private List<Cargo> mockCargos;
    private List<Truck> mockTrucks;

    @BeforeEach
    void setUp() {
        mockCargos = Arrays.asList(
                new Cargo('B',
                        List.of(List.of(' ', 'B', ' ', ' ', ' '),
                                List.of('В', 'В', 'В', 'В', 'В'),
                                List.of('В', 'B', ' ', 'В', 'В'))),
                new Cargo('Т',
                        List.of(List.of('Т'),
                                List.of('Т'),
                                List.of('Т'),
                                List.of('Т'),
                                List.of('Т'))));
//        mockTrucks = Arrays.asList(new Truck(), new Truck());


        when(cargoReader.read(anyString())).thenReturn(mockCargos);
    }


    @Test
    public void getLoadedTrucksListFromTxtFile_ValidInput_ReturnsLoadedTrucks() {
        TruckLoaderType truckLoaderType = TruckLoaderType.SIMPLE;
        when(truckLoaderType.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);

        List<Truck> result = loaderService.getLoadedTrucksListFromTxtFile("mockCargos", 2, truckLoaderType);

        assertThat(result).hasSize(2);
        verify(cargoReader, times(1)).read(anyString());
    }

    @Test
    void getLoadedTrucksListFromTxtFile_WithoutTruckLoaderType_ReturnsLoadedTrucks() {
        // Arrange
        when(TruckLoaderType.SIMPLE.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);

        // Act
        List<Truck> result = loaderService.getLoadedTrucksListFromTxtFile("mockCargos", 2);

        // Assert
        assertThat(result).hasSize(2);
        verify(cargoReader, times(1)).read(anyString());
    }

    @Test
    void getLoadedTrucksListFromTxtFile_WithoutMaxTruckNumber_ReturnsLoadedTrucks() {
        // Arrange
        when(TruckLoaderType.SIMPLE.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);

        // Act
        List<Truck> result = loaderService.getLoadedTrucksListFromTxtFile("mockCargos");

        // Assert
        assertThat(result).hasSize(2);
        verify(cargoReader, times(1)).read(anyString());
    }

    @Test
    void getLoadedTrucksStringFromTxtFile_ValidInput_ReturnsStringOfTrucks() {
        // Arrange
        TruckLoaderType truckLoaderType = TruckLoaderType.SIMPLE;
        when(truckLoaderType.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);
        String expectedString = mockTrucks.stream().map(Truck::toString).collect(Collectors.joining("\n\n"));

        // Act
        String result = loaderService.getLoadedTrucksStringFromTxtFile("mockCargos", 2, truckLoaderType);

        // Assert
        assertThat(result).isEqualTo(expectedString);
        verify(cargoReader, times(1)).read(anyString());
    }

    @Test
    void getLoadedTrucksStringFromTxtFile_WithoutTruckLoaderType_ReturnsStringOfTrucks() {
        // Arrange
        when(TruckLoaderType.SIMPLE.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);
        String expectedString = mockTrucks.stream().map(Truck::toString).collect(Collectors.joining("\n\n"));

        // Act
        String result = loaderService.getLoadedTrucksStringFromTxtFile("mockCargos", 2);

        // Assert
        assertThat(result).isEqualTo(expectedString);
        verify(cargoReader, times(1)).read(anyString());
    }

    @Test
    void getLoadedTrucksStringFromTxtFile_WithoutMaxTruckNumber_ReturnsStringOfTrucks() {
        // Arrange
        when(TruckLoaderType.SIMPLE.getTruckLoader().createLoadedTrucks(anyList(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockTrucks);
        String expectedString = mockTrucks.stream().map(Truck::toString).collect(Collectors.joining("\n\n"));

        // Act
        String result = loaderService.getLoadedTrucksStringFromTxtFile("mockCargos", TruckLoaderType.SIMPLE);

        // Assert
        assertThat(result).isEqualTo(expectedString);
        verify(cargoReader, times(1)).read(anyString());
    }
}