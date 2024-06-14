package ru.liga.bot.service;

import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.truck.entity.Truck;

import java.util.List;

public interface BotTruckLoaderService {
    List<Truck> createLoadedTrucks(LoaderBotDto loaderBotDto);
}
