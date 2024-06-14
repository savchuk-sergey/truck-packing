package ru.liga.cargo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.cargo.entity.Cargo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class StringCargoReaderTest {
    @InjectMocks
    private StringCargoReader stringCargoReader;

    @Test
    void read_ValidRawCargos_ReturnsListOfCargos() {
        String rawCargos = "A111\n111\n\nB222\n222";

        List<Cargo> result = stringCargoReader.read(rawCargos);

        assertThat(result).hasSize(2);

        Cargo firstCargo = result.get(0);
        assertThat(firstCargo.getTitle()).isEqualTo('A');
        assertThat(firstCargo.getSize()).containsExactly(
                List.of('A', '1', '1', '1'),
                List.of('1', '1', '1')
        );

        Cargo secondCargo = result.get(1);
        assertThat(secondCargo.getTitle()).isEqualTo('B');
        assertThat(secondCargo.getSize()).containsExactly(
                List.of('B', '2', '2', '2'),
                List.of('2', '2', '2')
        );
    }

    @Test
    void read_EmptyRawCargos_ReturnsEmptyList() {
        String rawCargos = "";

        List<Cargo> result = stringCargoReader.read(rawCargos);

        assertThat(result).isEmpty();
    }

    @Test
    void read_RawCargosWithTrailingNewline_ReturnsCorrectListOfCargos() {
        String rawCargos = "A111\n111\n\n";

        List<Cargo> result = stringCargoReader.read(rawCargos);

        assertThat(result).hasSize(1);

        Cargo firstCargo = result.get(0);
        assertThat(firstCargo.getTitle()).isEqualTo('A');
        assertThat(firstCargo.getSize()).containsExactly(
                List.of('A', '1', '1', '1'),
                List.of('1', '1', '1')
        );
    }

    @Test
    void read_MixedContentRawCargos_ReturnsCorrectListOfCargos() {
        String rawCargos = "A111\n111\nB222\n222";

        List<Cargo> result = stringCargoReader.read(rawCargos);

        assertThat(result).hasSize(2);

        Cargo firstCargo = result.get(0);
        assertThat(firstCargo.getTitle()).isEqualTo('A');
        assertThat(firstCargo.getSize()).containsExactly(
                List.of('A', '1', '1', '1'),
                List.of('1', '1', '1')
        );

        Cargo secondCargo = result.get(1);
        assertThat(secondCargo.getTitle()).isEqualTo('B');
        assertThat(secondCargo.getSize()).containsExactly(
                List.of('B', '2', '2', '2'),
                List.of('2', '2', '2')
        );
    }
}