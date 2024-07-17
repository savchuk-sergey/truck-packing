package ru.liga.bot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.bot.service.BotStateService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.statemachine.service.StateMachineService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.Variable;

@Component
@RequiredArgsConstructor
@Slf4j
public class BotTruckLoaderServiceImpl implements BotStateService {
    private final StateMachineService loaderStateService;

    public void goToNextStage(String updateMessage, BotCommandType botCommandType) {
        log.debug("updateMessage: {}, botCommandType: {}", updateMessage, botCommandType);
        if (!isCommandStarted()) {
            start(botCommandType);
            return;
        }
        loaderStateService.moveToNextStateWithMessage(MessageKey.DATA, updateMessage);
    }

    public String getBotResponseMessage() {
        return loaderStateService.getStringVariableValue(Variable.BOT_RESPONSE_MESSAGE);
    }

    private void start(BotCommandType botCommandType) {
        loaderStateService.sendEvent(Event.getEventByCommand(botCommandType));
    }

    private boolean isCommandStarted() {
        return loaderStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.LOADER);
    }
}
