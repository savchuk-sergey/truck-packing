package ru.liga.bot.handler;

import ru.liga.bot.type.BotCommandType;

public interface CommandHandler {
    String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType);
}
