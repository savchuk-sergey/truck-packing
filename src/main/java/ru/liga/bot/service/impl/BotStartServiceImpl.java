package ru.liga.bot.service.impl;

import org.springframework.stereotype.Component;
import ru.liga.bot.service.BotStartService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class BotStartServiceImpl implements BotStartService {
    public String start() {
        return String.format(BotMessagesType.COMMANDS_LIST.getText(),
                Arrays.stream(BotCommandType.values())
                        .map(BotCommandType::getCommand)
                        .collect(Collectors.joining("\n")));
    }
}
