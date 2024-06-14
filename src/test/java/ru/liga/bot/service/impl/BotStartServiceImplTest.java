package ru.liga.bot.service.impl;

import org.junit.jupiter.api.Test;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

import static org.assertj.core.api.Assertions.assertThat;

class BotStartServiceImplTest {

    @Test
    void start_ReturnsFormattedCommandsList() {
        BotStartServiceImpl botStartService = new BotStartServiceImpl();

        String result = botStartService.start();

        assertThat(result).isNotEmpty();
        assertThat(result).contains(BotMessagesType.COMMANDS_LIST.getText());
        assertThat(result).contains(BotCommandType.HELP.getCommand());
        assertThat(result).contains(BotCommandType.START.getCommand());
    }
}