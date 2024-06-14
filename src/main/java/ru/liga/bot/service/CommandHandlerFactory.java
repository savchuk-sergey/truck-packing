package ru.liga.bot.service;

import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.type.BotCommandType;

public interface CommandHandlerFactory {
    CommandHandler getHandler(BotCommandType.Type type);
}
