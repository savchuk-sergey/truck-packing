package ru.liga.bot.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.BotCargoTypeService;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

import java.util.List;

@Component
@Qualifier("cargoCommandHandler")
public class CargoCommandHandler implements CommandHandler {
    private final BotCargoTypeService botCargoTypeService;
    private final MessageServiceImpl messageService;
    private final UpdateMapper updateMapper;

    @Autowired
    public CargoCommandHandler(BotCargoTypeService botCargoTypeService, MessageServiceImpl messageService, UpdateMapper updateMapper) {
        this.botCargoTypeService = botCargoTypeService;
        this.messageService = messageService;
        this.updateMapper = updateMapper;
    }

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        CargoTypeBotDto cargoTypeBotDto = updateMapper.getCargoTypeBotDtoFromUpdateMessage(updateMessage);

        if (botCommandType.equals(BotCommandType.ADD_CARGO_TYPE)) {
            return handleAddCargoType(cargoTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.EDIT_CARGO_TYPE)) {
            return handleEditCargoType(cargoTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.DELETE_CARGO_TYPE)) {
            return handleDeleteCargoType(cargoTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.GET_CARGO_TYPES)) {
            return handleGetCargoTypes();
        } else {
            return BotMessagesType.CARGO_WRONG_TYPE_MESSAGE_FORMAT.getText();
        }
    }

    private String handleAddCargoType(CargoTypeBotDto cargoTypeBotDto) {
        botCargoTypeService.save(cargoTypeBotDto);
        return BotMessagesType.CARGO_TYPE_ADDED.getText();
    }

    private String handleEditCargoType(CargoTypeBotDto cargoTypeBotDto) {
        botCargoTypeService.update(cargoTypeBotDto);
        return BotMessagesType.CARGO_TYPE_EDITED.getText();
    }

    private String handleDeleteCargoType(CargoTypeBotDto cargoTypeBotDto) {
        botCargoTypeService.deleteById(cargoTypeBotDto.getId());
        return BotMessagesType.CARGO_TYPE_DELETED.getText();
    }

    private String handleGetCargoTypes() {
        List<CargoTypeBotDto> cargoTypeBotDtos = botCargoTypeService.findAll();
        return messageService.getMarkdownTableStringFromCargoTypeDtos(cargoTypeBotDtos);
    }
}