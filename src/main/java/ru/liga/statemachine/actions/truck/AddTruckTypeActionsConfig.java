package ru.liga.statemachine.actions.truck;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.mapper.TruckTypeBotMapper;
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
public class AddTruckTypeActionsConfig {
    private final TruckTypeService truckTypeService;
    private final TruckTypeBotMapper truckTypeBotMapper;
    private final ActionsService actionsService;


    @Bean
    public Action<State, Event> startAddTruckTypeAction() {
        return context -> {
            context.getStateMachine()
                    .getExtendedState()
                    .getVariables()
                    .put(
                            Variable.TRUCK_TYPE_BOT_DTO_BUILDER,
                            TruckTypeBotDto.builder()
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_PROVIDE_NAME.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputNameAddTruckTypeAction() {
        return context -> {
            actionsService.getTruckTypeBotDtoBuilderFromVariables(context)
                    .name(
                            actionsService.getStringValueFromMessageHeader(
                                    context.getMessage(), MessageKey.DATA
                            )
                    );
            actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_PROVIDE_HEIGHT.getText());
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
        };
    }

    @Bean
    public Action<State, Event> inputHeightAddTruckTypeAction() {
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
    public Action<State, Event> inputWidthAddTruckTypeAction() {
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
    public Action<State, Event> completeAddTruckTypeAction() {
        return context -> {
            try {
                save(actionsService.getTruckTypeBotDtoBuilderFromVariables(context).build());
                actionsService.setBotResponseMessageToVariable(context, BotMessageType.TRUCK_TYPE_ADDED.getText());
                log.debug("State Machine Variables: {}", context.getStateMachine()
                        .getExtendedState()
                        .getVariables());
            } catch (RuntimeException e) {
                actionsService.setBotResponseMessageToVariable(context, e.getMessage());
            }

        };
    }

    private void save(TruckTypeBotDto truckTypeBotDto) {
        truckTypeService.save(truckTypeBotMapper.toDto(truckTypeBotDto));
    }
}
