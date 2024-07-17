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
public class EditCargoTypeActionsConfig {
    private final CargoTypeService cargoTypeService;
    private final CargoTypeBotMapper cargoTypeBotMapper;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startEditCargoTypeAction() {
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
    public Action<State, Event> inputIdEditCargoTypeAction() {
        return context -> {
            actionsService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .id(
                            actionsService.getLongValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_PROVIDE_NAME.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputNameEditCargoTypeAction() {
        return context -> {
            actionsService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .name(
                            actionsService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_PROVIDE_REPRESENTATION.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputRepresentationEditCargoTypeAction() {
        return context -> {
            actionsService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .representation(
                            actionsService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );

            actionsService.sendEvent(context, Event.COMPLETE);
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> completeEditCargoTypeAction() {
        return context -> {
            try {
                update(actionsService.getCargoTypeBotDtoBuilderFromVariables(context).build());
                actionsService.setBotResponseMessageToVariable(context, BotMessageType.CARGO_TYPE_EDITED.getText());
                log.debug("State Machine Variables: {}", context.getStateMachine()
                        .getExtendedState()
                        .getVariables());
            } catch (RuntimeException e) {
                actionsService.setBotResponseMessageToVariable(context, e.getMessage());
            }
        };
    }

    private void update(CargoTypeBotDto cargoTypeBotDto) {
        cargoTypeService.update(cargoTypeBotDto.getId(), cargoTypeBotMapper.toDto(cargoTypeBotDto));
    }
}
