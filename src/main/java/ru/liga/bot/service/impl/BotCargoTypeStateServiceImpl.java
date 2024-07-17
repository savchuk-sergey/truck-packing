package ru.liga.bot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.liga.bot.service.BotStateService;
import ru.liga.bot.type.BotCommandType;
import ru.liga.statemachine.service.StateMachineService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.Variable;

@Component
@RequiredArgsConstructor
@Log4j2
public class BotCargoTypeStateServiceImpl implements BotStateService {
    private final StateMachineService cargoStateService;

    public void goToNextStage(String updateMessage, BotCommandType botCommandType) {
        log.debug("updateMessage: {}, botCommandType: {}", updateMessage, botCommandType);
        if (!isCommandStarted()) {
            start(botCommandType);
            return;
        }
        cargoStateService.moveToNextStateWithMessage(MessageKey.DATA, updateMessage);
    }

    public String getBotResponseMessage() {
        return cargoStateService.getStringVariableValue(Variable.BOT_RESPONSE_MESSAGE);
    }

    private void start(BotCommandType botCommandType) {
        cargoStateService.sendEvent(Event.getEventByCommand(botCommandType));
    }

    private boolean isCommandStarted() {
        return cargoStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.CARGO);
    }
}
