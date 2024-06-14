package ru.liga.cargo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.service.CargoReader;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class StringCargoReader implements CargoReader {
    public List<Cargo> read(String rawCargos) {
        log.trace("read - rawCargos: " + rawCargos);
        String[] rawCargosSplit = rawCargos.split("\n");
        List<List<Character>> cargoSizes = new ArrayList<>();
        List<Cargo> cargos = new ArrayList<>();

        for (int i = 0; i < rawCargosSplit.length; i++) {
            String line = rawCargosSplit[i].trim();
            log.trace("readCargoFromFile - file line: " + line);

            if (!line.isEmpty()) {
                cargoSizes.add(line.chars().mapToObj(c -> (char) c).toList());
            }
            if (line.isEmpty() || i == rawCargosSplit.length - 1) {
                cargos.add(getCargoFromList(cargoSizes));
                cargoSizes = new ArrayList<>();
            }
        }

        return cargos;
    }

    private Cargo getCargoFromList(List<List<Character>> sizes) {
        log.trace("getCargoFromList - sizes: " + sizes);
        char title = sizes.get(0).get(0);

        return new Cargo(title, sizes);
    }
}
