package ru.liga.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.bot.handler.impl.UpdateHandlerImpl;
import ru.liga.config.Config;
import ru.liga.truck.exception.TruckNumberExceededException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final Config config;
    private final UpdateHandlerImpl updateHandler;

    @Autowired
    public TelegramBot(Config config, UpdateHandlerImpl updateHandler) {
        super(config.getBotToken());
        this.config = config;
        this.updateHandler = updateHandler;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(updateHandler.createMessageForBotUpdate(update));
            } catch (TelegramApiException | TruckNumberExceededException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
