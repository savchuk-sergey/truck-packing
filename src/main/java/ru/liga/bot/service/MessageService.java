package ru.liga.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.type.BotCommandType;

import java.util.List;

public interface MessageService {
    String getMarkdownTableStringFromCargoTypeDtos(List<CargoTypeBotDto> cargoTypeDtos);

    String getMarkdownTableStringFromTruckTypeDtos(List<TruckTypeBotDto> truckTypeDtos);

    SendMessage createNewBotMessage(String message, Long chatId);

    BotCommandType getCommandFromBotMessage(String message);

    String getMarkdownCodeBlock(String message);
}
