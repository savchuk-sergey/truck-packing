package ru.liga.bot.handler.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoCommandHandlerTest {

    @Mock
    private BotCargoTypeService botCargoTypeService;

    @Mock
    private MessageServiceImpl messageService;

    @Mock
    private UpdateMapper updateMapper;

    @InjectMocks
    private CargoCommandHandler cargoCommandHandler;

    @Test
    public void createMessageForUserCommand_AddCargoType_ReturnsCargoTypeAddedMessage() {
        String updateMessage = "/add_cargo_type -и \"1\" -н \"TEST\" -п \"TEST\"";
        CargoTypeBotDto cargoTypeBotDto = new CargoTypeBotDto(1L, "TEST", "TEST");
        when(updateMapper.getCargoTypeBotDtoFromUpdateMessage(updateMessage)).thenReturn(cargoTypeBotDto);

        String result = cargoCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.ADD_CARGO_TYPE);

        assertThat(result).isEqualTo(BotMessageType.CARGO_TYPE_ADDED.getText());
    }

    @Test
    public void createMessageForUserCommand_WrongCommandType_ReturnsWrongTypeMessage() {
        String updateMessage = "some update message";
        String result = cargoCommandHandler.createMessageForUserCommand(updateMessage, BotCommandType.DEFAULT);

        assertThat(result).isEqualTo(BotMessageType.CARGO_WRONG_TYPE_MESSAGE_FORMAT.getText());
    }

    @Test
    public void handleGetCargoTypes_ReturnsMarkdownTableString() {
        List<CargoTypeBotDto> cargoTypeBotDtos = List.of(new CargoTypeBotDto(1L, "TEST", "TEST"));
        when(botCargoTypeService.findAll()).thenReturn(cargoTypeBotDtos);
        when(messageService.getMarkdownTableStringFromCargoTypeDtos(cargoTypeBotDtos)).thenReturn("""
                ID         NAME                                               REPRESENTATION
                1          TEST                                               TEST         \s""");

        String result = cargoCommandHandler.createMessageForUserCommand("", BotCommandType.GET_CARGO_TYPES);

        assertThat(result).contains("ID         NAME                                               REPRESENTATION");
    }
}