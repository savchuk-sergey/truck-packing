package ru.liga.statemachine.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.statemachine.service.ActionsService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CompleteActionsConfig {
    private final ActionsService actionsService;

    @Bean
    public Action<State, Event> completeAction() {
        return context -> {
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
            actionsService.sendEvent(context, Event.RESET);
        };
    }
}
