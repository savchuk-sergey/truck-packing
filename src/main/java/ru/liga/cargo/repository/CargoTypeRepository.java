package ru.liga.cargo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.cargo.entity.CargoType;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoTypeRepository extends CrudRepository<CargoType, Long> {
    Optional<CargoType> findByName(String name);

    List<CargoType> findByNameIn(List<String> names);
}
