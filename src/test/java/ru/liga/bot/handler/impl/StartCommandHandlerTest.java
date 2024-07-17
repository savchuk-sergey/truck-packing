package ru.liga.bot.handler.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.service.BotStartService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StartCommandHandlerTest {

    @Mock
    private BotStartService botStartService;

    @InjectMocks
    private StartCommandHandler startCommandHandler;

    @Test
    public void createMessageForUserCommand_Start_ReturnsStartMessage() {
        when(botStartService.start()).thenReturn("test");
        String result = startCommandHandler.createMessageForUserCommand("some update message", BotCommandType.START);
        assertThat(result).isEqualTo("test");
    }

    @Test
    public void createMessageForUserCommand_WrongCommandType_ReturnsWrongMessageFormat() {
        String result = startCommandHandler.createMessageForUserCommand("some update message", BotCommandType.DEFAULT);
        assertThat(result).isEqualTo(BotMessageType.CARGO_WRONG_TYPE_MESSAGE_FORMAT.getText());
    }
}