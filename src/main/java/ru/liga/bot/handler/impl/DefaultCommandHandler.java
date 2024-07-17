package ru.liga.bot.handler.impl;

import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;

@Component
public class DefaultCommandHandler implements CommandHandler {
    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        return String.format(BotMessageType.WRONG_COMMAND.getText(), botCommandType);
    }
}
