package ru.liga.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.BotStartService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final BotStartService botStartService;

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        if (botCommandType.equals(BotCommandType.START)) {
            return handleStart();
        } else {
            return BotMessageType.CARGO_WRONG_TYPE_MESSAGE_FORMAT.getText();
        }
    }

    private String handleStart() {
        return botStartService.start();
    }
}
