package ru.liga.statemachine.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class StartActionsConfig {

    @Bean
    public Action<State, Event> startAction() {
        return context -> {
            log.debug("State Machine Variables: {}", context.getStateMachine()
                    .getExtendedState()
                    .getVariables());
            context.getExtendedState().getVariables().clear();
        };
    }
}