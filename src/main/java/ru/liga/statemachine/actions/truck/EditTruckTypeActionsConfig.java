package ru.liga.statemachine.actions.truck;

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
public class EditTruckTypeActionsConfig {
    private final CargoTypeService cargoTypeService;
    private final CargoTypeBotMapper cargoTypeBotMapper;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startEditTruckTypeAction() {
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
    public Action<State, Event> inputIdEditTruckTypeAction() {
        return context -> {
            actionsService.getCargoTypeBotDtoBuilderFromVariables(context)
                    .id(
                            actionsService.getLongValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
        };
    }

    @Bean
    public Action<State, Event> inputNameEditTruckTypeAction() {
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
    public Action<State, Event> inputHeightEditTruckTypeAction() {
        return context -> {
            actionsService.getTruckTypeBotDtoBuilderFromVariables(context)
                    .height(
                            actionsService.getIntValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );

            actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_PROVIDE_WIDTH.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputWidthEditTruckTypeAction() {
        return context -> {
            actionsService.getTruckTypeBotDtoBuilderFromVariables(context)
                    .width(
                            actionsService.getIntValueFromMessageHeader(
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
    public Action<State, Event> completeEditTruckTypeAction() {
        return context -> {
            update(actionsService.getCargoTypeBotDtoBuilderFromVariables(context).build());
            context.getExtendedState().getVariables().clear();
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    private void update(CargoTypeBotDto cargoTypeBotDto) {
        cargoTypeService.update(cargoTypeBotDto.getId(), cargoTypeBotMapper.toDto(cargoTypeBotDto));
    }
}
