package ru.liga.bot.handler.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.BotTruckTypeService;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckCommandHandlerTest {

    @Mock
    private BotTruckTypeService botTruckTypeService;

    @Mock
    private MessageServiceImpl messageService;

    @Mock
    private UpdateMapper updateMapper;

    @InjectMocks
    private TruckCommandHandler truckCommandHandler;

    @Test
    public void createMessageForUserCommand_AddTruckType_ReturnsTruckTypeAddedMessage() {
        String updateMessage = "some update message";
        TruckTypeBotDto truckTypeBotDto = new TruckTypeBotDto(1L, "TEST", 1, 1);
        when(updateMapper.getTruckTypeBotDtoFromUpdateMessage(updateMessage)).thenReturn(truckTypeBotDto);

        String result = truckCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.ADD_TRUCK_TYPE);

        assertThat(result).isEqualTo(BotMessagesType.TRUCK_TYPE_ADDED.getText());
    }

    @Test
    public void createMessageForUserCommand_WrongCommandType_ReturnsWrongTypeMessage() {
        String updateMessage = "some update message";
        String result = truckCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.DEFAULT);

        assertThat(result).isEqualTo(BotMessagesType.TRUCK_WRONG_TYPE_MESSAGE_FORMAT.getText());
    }

    @Test
    public void handleGetTruckTypes_ReturnsMarkdownTableString() {
        List<TruckTypeBotDto> truckTypeBotDtos = List.of(new TruckTypeBotDto(1L, "TEST", 1, 1));
        when(botTruckTypeService.findAll()).thenReturn(truckTypeBotDtos);
        when(messageService.getMarkdownTableStringFromTruckTypeDtos(truckTypeBotDtos)).thenReturn("");
        String result = truckCommandHandler.createMessageForUserCommand("", BotCommandType.GET_TRUCK_TYPES);

        assertThat(result).contains("| ID | NAME | WIDTH | HEIGHT |");
    }
}