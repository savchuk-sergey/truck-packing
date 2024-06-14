package ru.liga.truck.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.liga.truck.entity.TruckType;

@Repository
public interface TruckTypeRepository extends CrudRepository<TruckType, Long> {
    TruckType findByName(String name);
}
