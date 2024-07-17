package ru.liga.bot.service;

import ru.liga.bot.type.BotCommandType;

public interface BotStateService {
    void goToNextStage(String updateMessage, BotCommandType botCommandType);

    String getBotResponseMessage();
}
