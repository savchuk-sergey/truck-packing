package ru.liga.statemachine.actions.cargo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.mapper.CargoTypeBotMapper;
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
public class AddCargoTypeActionsConfig {
    private final CargoTypeService cargoTypeService;
    private final CargoTypeBotMapper cargoTypeBotMapper;
    private final ActionsService actionService;

    @Bean
    public Action<State, Event> startAddCargoTypeAction() {
        return context -> {
            context.getStateMachine()
                    .getExtendedState()
                    .getVariables()
                    .put(
                            Variable.CARGO_TYPE_BOT_DTO_BUILDER,
                            CargoTypeBotDto.builder()
                    );
            actionService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_PROVIDE_NAME.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputNameAddCargoTypeAction() {
        return context -> {
            actionService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .name(
                            actionService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_PROVIDE_REPRESENTATION.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputRepresentationAddCargoTypeAction() {
        return context -> {
            actionService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .representation(
                            actionService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );

            actionService.sendEvent(context, Event.COMPLETE);
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> completeAddCargoTypeAction() {
        return context -> {
            try {
                save(actionService.getCargoTypeBotDtoBuilderFromVariables(context).build());
                actionService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_ADDED.getText());
                log.debug("State Machine Variables: {}", context.getStateMachine()
                        .getExtendedState()
                        .getVariables());
            } catch (RuntimeException e) {
                actionService.setBotResponseMessageToVariable(context, e.getMessage());
            }
        };
    }

    private void save(CargoTypeBotDto cargoTypeBotDto) {
        cargoTypeService.save(cargoTypeBotMapper.toDto(cargoTypeBotDto));
    }
}
