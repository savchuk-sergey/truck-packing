package ru.liga.bot.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.mapper.UpdateMapper;
import ru.liga.bot.service.MessageService;
import ru.liga.bot.service.impl.BotTruckLoaderServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;
import ru.liga.truck.entity.Truck;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("loaderCommandHandler")
public class LoaderCommandHandler implements CommandHandler {
    private final BotTruckLoaderServiceImpl botTruckLoaderServiceImpl;
    private final UpdateMapper updateMapper;
    private final MessageService messageService;

    @Autowired
    public LoaderCommandHandler(BotTruckLoaderServiceImpl botTruckLoaderServiceImpl, UpdateMapper updateMapper, MessageService messageService) {
        this.botTruckLoaderServiceImpl = botTruckLoaderServiceImpl;
        this.updateMapper = updateMapper;
        this.messageService = messageService;
    }

    public String createMessageForUserCommand(String updateMessage, BotCommandType botCommandType) {
        LoaderBotDto loaderBotDto = updateMapper.getLoaderBotDtoFromUpdateMessage(updateMessage);

        if (botCommandType.equals(BotCommandType.LOAD_TRUCKS)) {
            return handleLoadTrucks(loaderBotDto);
        } else {
            return BotMessagesType.LOADER_WRONG_MESSAGE_FORMAT.getText();
        }
    }

    private String handleLoadTrucks(LoaderBotDto loaderBotDto) {
        List<Truck> trucks = botTruckLoaderServiceImpl.createLoadedTrucks(loaderBotDto);
        return messageService.getMarkdownCodeBlock(
                trucks.stream()
                        .map(Truck::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}