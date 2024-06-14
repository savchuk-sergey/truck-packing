package ru.liga.cargo.service;

import ru.liga.cargo.entity.Cargo;

import java.util.List;

public interface CargoReader {
    List<Cargo> read(String rawCargos);
}
