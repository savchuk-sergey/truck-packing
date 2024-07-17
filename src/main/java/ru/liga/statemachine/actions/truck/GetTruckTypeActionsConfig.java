package ru.liga.statemachine.actions.truck;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.mapper.TruckTypeBotMapper;
import ru.liga.bot.service.MessageService;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;
import ru.liga.truck.service.TruckTypeService;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class GetTruckTypeActionsConfig {
    private final TruckTypeService truckTypeService;
    private final TruckTypeBotMapper truckTypeBotMapper;
    private final MessageService messageService;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startGetTruckTypeAction() {
        return context -> {
            log.debug("Context.Current State: {}", context.getStateMachine().getState().getId());
            actionsService.setBotResponseMessageToVariable(context, messageService.getMarkdownTableStringFromTruckTypeDtos(findAll()));
            actionsService.sendEvent(context, Event.COMPLETE);
            actionsService.sendEvent(context, Event.RESET);
        };
    }

    private List<TruckTypeBotDto> findAll() {
        return truckTypeService.findAll().stream()
                .map(truckTypeBotMapper::toBotDto)
                .toList();
    }
}
