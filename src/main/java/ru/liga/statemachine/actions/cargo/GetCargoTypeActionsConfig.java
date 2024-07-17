package ru.liga.statemachine.actions.cargo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.mapper.CargoTypeBotMapper;
import ru.liga.bot.service.MessageService;
import ru.liga.cargo.service.CargoTypeService;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class GetCargoTypeActionsConfig {
    private final CargoTypeService cargoTypeService;
    private final CargoTypeBotMapper cargoTypeBotMapper;
    private final MessageService messageService;
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> startGetCargoTypeAction() {
        return context -> {
            log.debug("Context.Current State: {}", context.getStateMachine().getState().getId());
            actionsService.setBotResponseMessageToVariable(context, messageService.getMarkdownTableStringFromCargoTypeDtos(findAll()));
            actionsService.sendEvent(context, Event.COMPLETE);
            actionsService.sendEvent(context, Event.RESET);
        };
    }

    private List<CargoTypeBotDto> findAll() {
        return cargoTypeService.findAll().stream()
                .map(cargoTypeBotMapper::toBotDto)
                .toList();
    }
}
