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
public class BotTruckTypeStateServiceImpl implements BotStateService {
    private final StateMachineService truckStateService;

    public void goToNextStage(String updateMessage, BotCommandType botCommandType) {
        log.debug("updateMessage: {}, botCommandType: {}", updateMessage, botCommandType);
        if (!isCommandStarted()) {
            start(botCommandType);
            return;
        }
        truckStateService.moveToNextStateWithMessage(MessageKey.DATA, updateMessage);
    }

    public String getBotResponseMessage() {
        return truckStateService.getStringVariableValue(Variable.BOT_RESPONSE_MESSAGE);
    }

    private void start(BotCommandType botCommandType) {
        truckStateService.sendEvent(Event.getEventByCommand(botCommandType));
    }

    private boolean isCommandStarted() {
        return truckStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.TRUCK);
    }
}
