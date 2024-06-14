package ru.liga.truck.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.cargo.entity.Cargo;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.exception.TruckNumberExceededException;
import ru.liga.truck.service.TruckLoader;
import ru.liga.truck.type.TruckLoaderType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class SimpleTruckLoaderTest {

    private TruckLoader simpleTruckLoader;

    @BeforeEach
    void setUp() {
        simpleTruckLoader = TruckLoaderType.SIMPLE.getTruckLoader();
    }

    @Test
    void createLoadedTrucks_ValidInput_ReturnsLoadedTrucks() throws TruckNumberExceededException {
        Cargo cargo = new Cargo('В',
                List.of(List.of(' ', 'В', ' ', ' ', ' '),
                        List.of('В', 'В', 'В', 'В', 'В'),
                        List.of('В', 'В', ' ', 'В', 'В')));
        List<Cargo> cargos = Collections.singletonList(cargo);
        List<Truck> result = simpleTruckLoader.createLoadedTrucks(cargos, 6, 6, 5);

        assertThat(result).hasSize(1);
        Truck truck = result.get(0);
        assertThat(truck.toString()).isEqualTo(
                """
                        +      +
                        +      +
                        +      +
                        + В    +
                        +ВВВВВ +
                        +ВВ ВВ +
                        ++++++++
                        """
        );
    }

    @Test
    void createLoadedTrucks_ExceedsMaxTruckNumber_ThrowsException() {
        Cargo cargo1 = new Cargo('B',
                List.of(List.of(' ', 'B', ' ', ' ', ' '),
                        List.of('В', 'В', 'В', 'В', 'В'),
                        List.of('В', 'B', ' ', 'В', 'В')));
        Cargo cargo2 = new Cargo('B',
                List.of(List.of(' ', 'B', ' ', ' ', ' '),
                        List.of('В', 'В', 'В', 'В', 'В'),
                        List.of('В', 'B', ' ', 'В', 'В')));

        List<Cargo> cargos = Arrays.asList(cargo1, cargo2);

        assertThatThrownBy(() -> simpleTruckLoader.createLoadedTrucks(cargos, 4, 5, 1))
                .isInstanceOf(TruckNumberExceededException.class)
                .hasMessageContaining("1");
    }

    @Test
    void createLoadedTrucks_EmptyCargoList_ReturnsEmptyTruckList() throws TruckNumberExceededException {
        List<Cargo> cargos = Collections.emptyList();
        List<Truck> result = simpleTruckLoader.createLoadedTrucks(cargos, 10, 10, 5);

        assertThat(result).isEmpty();
    }

    @Test
    void createLoadedTrucks_SingleCargoFitsInOneTruck() throws TruckNumberExceededException {
        Cargo cargo = new Cargo('B',
                List.of(List.of(' ', 'B', ' ', ' ', ' '),
                        List.of('В', 'В', 'В', 'В', 'В'),
                        List.of('В', 'B', ' ', 'В', 'В')));

        List<Cargo> cargos = Collections.singletonList(cargo);
        List<Truck> result = simpleTruckLoader.createLoadedTrucks(cargos, 4, 5, 5);

        assertThat(result).hasSize(1);
    }
}