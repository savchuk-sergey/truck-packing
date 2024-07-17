package ru.liga.statemachine.actions.cargo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.type.BotMessageType;
import ru.liga.cargo.service.CargoTypeService;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.State;
import ru.liga.statemachine.type.Variable;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DeleteCargoTypeActionsConfig {
    private final CargoTypeService cargoTypeService;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startDeleteCargoTypeAction() {
        return context -> {
            context.getStateMachine()
                    .getExtendedState()
                    .getVariables()
                    .put(
                            Variable.CARGO_TYPE_BOT_DTO_BUILDER,
                            CargoTypeBotDto.builder()
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_PROVIDE_ID.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputIdDeleteCargoTypeAction() {
        return context -> {
            deleteById(actionsService.getLongValueFromMessageHeader(context.getMessage(), MessageKey.DATA));
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_DELETED.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> completeDeleteCargoTypeAction() {
        return context -> {
            actionsService.sendEvent(context, Event.RESET);
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    private void deleteById(Long id) {
        cargoTypeService.deleteById(id);
    }
}
