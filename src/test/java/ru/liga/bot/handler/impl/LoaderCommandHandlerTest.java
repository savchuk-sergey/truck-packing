package ru.liga.bot.handler.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.MessageService;
import ru.liga.bot.service.impl.BotTruckLoaderServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;
import ru.liga.truck.entity.Truck;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoaderCommandHandlerTest {

    @Mock
    private BotTruckLoaderServiceImpl botTruckLoaderService;

    @Mock
    private UpdateMapper updateMapper;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private LoaderCommandHandler loaderCommandHandler;

    @Test
    public void createMessageForUserCommand_LoadTrucks_ReturnsMarkdownCodeBlock() {
        String updateMessage = "some update message";
        LoaderBotDto loaderBotDto = new LoaderBotDto("TEST", 1, "SIMPLE", List.of("TEST1", "TEST2"));
        List<Truck> trucks = List.of(new Truck(List.of(List.of('C')), 1, 1));
        when(updateMapper.getLoaderBotDtoFromUpdateMessage(updateMessage)).thenReturn(loaderBotDto);
        when(botTruckLoaderService.createLoadedTrucks(loaderBotDto)).thenReturn(trucks);
        when(messageService.getMarkdownCodeBlock(trucks.stream().map(Truck::toString).collect(Collectors.joining("\n"))))
                .thenReturn("""
                        ```
                        123
                        ```""");

        String result = loaderCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.LOAD_TRUCKS);

        assertThat(result).isEqualTo("123");
    }

    @Test
    public void createMessageForUserCommand_WrongCommandType_ReturnsWrongMessageFormat() {
        String updateMessage = "some update message";

        String result = loaderCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.DEFAULT);

        assertThat(result).isEqualTo(BotMessageType.LOADER_WRONG_MESSAGE_FORMAT.getText());
    }
}