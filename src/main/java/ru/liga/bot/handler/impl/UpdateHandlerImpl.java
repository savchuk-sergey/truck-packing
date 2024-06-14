package ru.liga.bot.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.bot.handler.UpdateHandler;
import ru.liga.bot.service.impl.CommandHandlerFactoryImpl;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessagesType;
import ru.liga.truck.exception.TruckNumberExceededException;

@Component
@Slf4j
public class UpdateHandlerImpl implements UpdateHandler {
    private final CommandHandlerFactoryImpl commandHandlerFactoryImpl;
    private final MessageServiceImpl messageService;

    @Autowired
    public UpdateHandlerImpl(CommandHandlerFactoryImpl commandHandlerFactoryImpl, MessageServiceImpl messageService) {
        this.commandHandlerFactoryImpl = commandHandlerFactoryImpl;
        this.messageService = messageService;
    }

    public SendMessage createMessageForBotUpdate(Update update) throws TruckNumberExceededException {
        String userInput = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        try {
            BotCommandType botCommandType = messageService.getCommandFromBotMessage(userInput);
            return messageService.createNewBotMessage(
                    commandHandlerFactoryImpl
                            .getHandler(botCommandType.getType())
                            .createMessageForUserCommand(userInput, botCommandType),
                    chatId);
        } catch (RuntimeException exception) {
            String errorMessage = String.format(BotMessagesType.ERROR.getText(), exception.getMessage());
            log.error(errorMessage);
            return messageService.createNewBotMessage(errorMessage, chatId);
        }
    }
}
