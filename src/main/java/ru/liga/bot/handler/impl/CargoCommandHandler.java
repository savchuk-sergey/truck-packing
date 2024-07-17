package ru.liga.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.BotStateService;
import ru.liga.bot.type.BotCommandType;

@Component
@RequiredArgsConstructor
@Slf4j
public class CargoCommandHandler implements CommandHandler {
    private final BotStateService botCargoTypeStateServiceImpl;

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        botCargoTypeStateServiceImpl.goToNextStage(updateMessage, botCommandType);
        return botCargoTypeStateServiceImpl.getBotResponseMessage();
    }
}