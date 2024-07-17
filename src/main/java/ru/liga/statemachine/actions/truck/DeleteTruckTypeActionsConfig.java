package ru.liga.statemachine.actions.truck;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.type.BotMessageType;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.State;
import ru.liga.statemachine.type.Variable;
import ru.liga.truck.service.TruckTypeService;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DeleteTruckTypeActionsConfig {
    private final TruckTypeService truckTypeService;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startDeleteTruckTypeAction() {
        return context -> {
            context.getStateMachine()
                    .getExtendedState()
                    .getVariables()
                    .put(
                            Variable.TRUCK_TYPE_BOT_DTO_BUILDER,
                            TruckTypeBotDto.builder()
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_PROVIDE_ID.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputIdDeleteTruckTypeAction() {
        return context -> {
            deleteById(actionsService.getLongValueFromMessageHeader(context.getMessage(), MessageKey.DATA));
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_DELETED.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> completeDeleteTruckTypeAction() {
        return context -> {
            actionsService.sendEvent(context, Event.RESET);
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    private void deleteById(Long id) {
        truckTypeService.deleteById(id);
    }
}