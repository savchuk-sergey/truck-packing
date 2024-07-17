package ru.liga.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.BotStateService;
import ru.liga.bot.type.BotCommandType;

@Component
@RequiredArgsConstructor
public class LoaderCommandHandler implements CommandHandler {
    private final BotStateService botTruckLoaderServiceImpl;

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        botTruckLoaderServiceImpl.goToNextStage(updateMessage, botCommandType);
        return botTruckLoaderServiceImpl.getBotResponseMessage();
    }
}