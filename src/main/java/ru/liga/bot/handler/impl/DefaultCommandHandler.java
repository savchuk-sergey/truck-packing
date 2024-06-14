package ru.liga.bot.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.type.BotCommandType;

@Component
@Qualifier("defaultCommandHandler")
public class DefaultCommandHandler implements CommandHandler {
    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        return null;
    }
}
