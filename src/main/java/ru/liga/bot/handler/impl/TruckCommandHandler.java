package ru.liga.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.BotStateService;
import ru.liga.bot.type.BotCommandType;

@Component
@RequiredArgsConstructor
public class TruckCommandHandler implements CommandHandler {
    private final BotStateService botTruckTypeStateServiceImpl;

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        botTruckTypeStateServiceImpl.goToNextStage(updateMessage, botCommandType);
        return botTruckTypeStateServiceImpl.getBotResponseMessage();
    }
}