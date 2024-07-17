package ru.liga.bot.handler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.bot.handler.UpdateHandler;
import ru.liga.bot.service.impl.CommandHandlerFactoryImpl;
import ru.liga.bot.service.impl.MessageServiceImpl;
import ru.liga.bot.type.BotCommandType;
import ru.liga.bot.type.BotMessageType;
import ru.liga.truck.exception.TruckNumberExceededException;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateHandlerImpl implements UpdateHandler {
    private final CommandHandlerFactoryImpl commandHandlerFactoryImpl;
    private final MessageServiceImpl messageService;

    public SendMessage createMessageForBotUpdate(Update update) throws TruckNumberExceededException {
        String userInput = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        try {
            BotCommandType botCommandType = messageService.getCommandFromBotMessage(userInput);
            log.debug("Bot Command: {}", botCommandType);
            return messageService.createNewBotMessage(
                    commandHandlerFactoryImpl
                            .getHandler(botCommandType)
                            .createMessageForUserCommand(userInput, botCommandType),
                    chatId);
        } catch (RuntimeException exception) {
            String errorMessage = String.format(BotMessageType.ERROR.getText(), exception.getMessage());
            log.error(errorMessage);
            return messageService.createNewBotMessage(errorMessage, chatId);
        }
    }
}
