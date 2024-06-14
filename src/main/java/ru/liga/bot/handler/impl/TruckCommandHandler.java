package ru.liga.bot.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.BotTruckTypeService;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;

import java.util.List;

@Component
@Qualifier("truckCommandHandler")
public class TruckCommandHandler implements CommandHandler {
    private final BotTruckTypeService botTruckTypeService;
    private final MessageServiceImpl messageService;
    private final UpdateMapper updateMapper;

    @Autowired
    public TruckCommandHandler(BotTruckTypeService botTruckTypeService, MessageServiceImpl messageService, UpdateMapper updateMapper) {
        this.botTruckTypeService = botTruckTypeService;
        this.messageService = messageService;
        this.updateMapper = updateMapper;
    }

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        TruckTypeBotDto truckTypeBotDto = updateMapper.getTruckTypeBotDtoFromUpdateMessage(updateMessage);

        if (botCommandType.equals(BotCommandType.ADD_TRUCK_TYPE)) {
            return handleAddTruckType(truckTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.EDIT_TRUCK_TYPE)) {
            return handleEditTruckType(truckTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.DELETE_TRUCK_TYPE)) {
            return handleDeleteTruckType(truckTypeBotDto);
        } else if (botCommandType.equals(BotCommandType.GET_TRUCK_TYPES)) {
            return handleGetTruckTypes();
        } else {
            return BotMessagesType.TRUCK_WRONG_TYPE_MESSAGE_FORMAT.getText();
        }
    }

    private String handleAddTruckType(TruckTypeBotDto truckTypeBotDto) {
        botTruckTypeService.save(truckTypeBotDto);
        return BotMessagesType.TRUCK_TYPE_ADDED.getText();
    }

    private String handleEditTruckType(TruckTypeBotDto truckTypeBotDto) {
        botTruckTypeService.update(truckTypeBotDto);
        return BotMessagesType.TRUCK_TYPE_EDITED.getText();
    }

    private String handleDeleteTruckType(TruckTypeBotDto truckTypeBotDto) {
        botTruckTypeService.deleteById(truckTypeBotDto.getId());
        return BotMessagesType.TRUCK_TYPE_DELETED.getText();
    }

    private String handleGetTruckTypes() {
        List<TruckTypeBotDto> truckTypeBotDtos = botTruckTypeService.findAll();
        return messageService.getMarkdownTableStringFromTruckTypeDtos(truckTypeBotDtos);
    }
}