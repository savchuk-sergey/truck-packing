package ru.liga.statemachine.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import ru.liga.statemachine.service.StateMachineService;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

@Configuration
@RequiredArgsConstructor
public class StateMachineServiceConfig {
    private final StateMachine<State, Event> cargoStateMachine;
    private final StateMachine<State, Event> truckStateMachine;
    private final StateMachine<State, Event> loaderStateMachine;

    @Bean
    public StateMachineService cargoStateService() {
        return new StateMachineService(cargoStateMachine);
    }

    @Bean
    public StateMachineService truckStateService() {
        return new StateMachineService(truckStateMachine);
    }

    @Bean
    public StateMachineService loaderStateService() {
        return new StateMachineService(loaderStateMachine);
    }
}
