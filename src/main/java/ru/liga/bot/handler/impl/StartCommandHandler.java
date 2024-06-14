package ru.liga.bot.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.BotStartService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

@Component
@Qualifier("startCommandHandler")
public class StartCommandHandler implements CommandHandler {
    private final BotStartService botStartService;

    @Autowired
    public StartCommandHandler(BotStartService botStartService) {
        this.botStartService = botStartService;
    }

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        if (botCommandType.equals(BotCommandType.START)) {
            return handleStart();
        } else {
            return BotMessagesType.CARGO_WRONG_TYPE_MESSAGE_FORMAT.getText();
        }
    }

    private String handleStart() {
        return botStartService.start();
    }
}
