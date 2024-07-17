package ru.liga.statemachine.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import ru.liga.statemachine.actions.CompleteActionsConfig;
import ru.liga.statemachine.actions.StartActionsConfig;
import ru.liga.statemachine.actions.loader.LoaderActionsConfig;
import ru.liga.statemachine.listener.StateMachineErrorListener;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachine(name = "loaderStateMachine")
public class LoaderStateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {
    private final LoaderActionsConfig loaderActionsConfig;
    private final CompleteActionsConfig completeActionsConfig;
    private final StartActionsConfig startActionsConfig;

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(stateMachineErrorListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
                .withStates()
                .initial(State.INITIAL)
                .stateExit(State.INITIAL, startActionsConfig.startAction())
                .states(EnumSet.allOf(State.class))

                .stateEntry(State.LOADER_IN_PROCESS, loaderActionsConfig.startLoadingTrucksAction())
                .stateEntry(State.COMPLETED, completeActionsConfig.completeAction())

                .end(State.STOP);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions
                //Reset
                .withExternal()
                .source(State.COMPLETED).target(State.INITIAL)
                .event(Event.RESET)
                .and()
                //Loader
                .withExternal()
                .source(State.INITIAL).target(State.LOADER_IN_PROCESS)
                .event(Event.LOAD_TRUCKS)
                .and()

                .withExternal()
                .source(State.LOADER_IN_PROCESS).target(State.TRUCK_TYPE_PROVIDED)
                .event(Event.PROVIDE_TRUCK_TYPE_NAME)
                .action(loaderActionsConfig.inputTruckTypeLoaderAction())
                .and()

                .withExternal()
                .source(State.TRUCK_TYPE_PROVIDED).target(State.TRUCK_COUNT_PROVIDED)
                .event(Event.PROVIDE_TRUCK_COUNT)
                .action(loaderActionsConfig.inputTruckNumberLoaderAction())
                .and()

                .withExternal()
                .source(State.TRUCK_COUNT_PROVIDED).target(State.LOADER_TYPE_PROVIDED)
                .event(Event.PROVIDE_LOADER_TYPE)
                .action(loaderActionsConfig.inputTypeLoaderAction())
                .and()

                .withExternal()
                .source(State.LOADER_TYPE_PROVIDED).target(State.CARGOS_PROVIDED)
                .event(Event.PROVIDE_CARGOS)
                .action(loaderActionsConfig.inputCargosLoaderAction())
                .and()

                .withExternal()
                .source(State.CARGOS_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(loaderActionsConfig.completeLoaderAction());
    }

    @Bean
    public StateMachineListenerAdapter<State, Event> stateMachineErrorListener() {
        return new StateMachineErrorListener();
    }
}
