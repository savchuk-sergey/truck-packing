package ru.liga.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.service.BotTruckLoaderService;
import ru.liga.cargo.entity.Cargo;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.mapper.CargoTypeMapper;
import ru.liga.cargo.service.CargoTypeService;
import ru.liga.truck.entity.Truck;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.mapper.TruckTypeMapper;
import ru.liga.truck.service.TruckLoader;
import ru.liga.truck.service.TruckTypeService;
import ru.liga.truck.type.TruckLoaderType;

import java.util.List;

@Component
public class BotTruckLoaderServiceImpl implements BotTruckLoaderService {
    private final CargoTypeMapper cargoTypeMapper;
    private final TruckTypeService truckTypeService;
    private final CargoTypeService cargoTypeService;
    private final TruckTypeMapper truckTypeMapper;

    @Autowired
    public BotTruckLoaderServiceImpl(CargoTypeMapper cargoTypeMapper, TruckTypeService truckTypeService, CargoTypeService cargoTypeService, TruckTypeMapper truckTypeMapper) {
        this.cargoTypeMapper = cargoTypeMapper;
        this.truckTypeService = truckTypeService;
        this.cargoTypeService = cargoTypeService;
        this.truckTypeMapper = truckTypeMapper;
    }

    public List<Truck> createLoadedTrucks(LoaderBotDto loaderBotDto) {
        TruckLoader truckLoader = TruckLoaderType.findByTypeName(loaderBotDto.getAlgorithmType().toUpperCase());

        List<CargoType> cargoTypes = cargoTypeService.findByNameIn(loaderBotDto.getCargoNames());

        List<Cargo> cargos = cargoTypes.stream()
                .map(cargoTypeMapper::cargoTypeToCargo)
                .toList();

        TruckType truckType = truckTypeMapper.toEntity(truckTypeService.findByName(loaderBotDto.getTruckName()));

        return truckLoader.createLoadedTrucks(
                cargos,
                truckType.getHeight(),
                truckType.getWidth(),
                loaderBotDto.getTruckNumber()
        );
    }
}
