package ru.liga.statemachine.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import ru.liga.statemachine.actions.CompleteActionsConfig;
import ru.liga.statemachine.actions.StartActionsConfig;
import ru.liga.statemachine.actions.cargo.AddCargoTypeActionsConfig;
import ru.liga.statemachine.actions.cargo.DeleteCargoTypeActionsConfig;
import ru.liga.statemachine.actions.cargo.EditCargoTypeActionsConfig;
import ru.liga.statemachine.actions.cargo.GetCargoTypeActionsConfig;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachine(name = "cargoStateMachine")
public class CargoStateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {
    private final AddCargoTypeActionsConfig addCargoTypeActionsConfig;
    private final DeleteCargoTypeActionsConfig deleteCargoTypeActionsConfig;
    private final EditCargoTypeActionsConfig editCargoTypeActionsConfig;
    private final GetCargoTypeActionsConfig getCargoTypeActionsConfig;
    private final CompleteActionsConfig completeActionsConfig;
    private final StartActionsConfig startActionsConfig;

    @Override
    public void configure(StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states
                .withStates()
                .initial(State.INITIAL)
                .stateExit(State.INITIAL, startActionsConfig.startAction())
                .states(EnumSet.allOf(State.class))

                .stateEntry(State.ADD_CARGO_TYPE_IN_PROCESS, addCargoTypeActionsConfig.startAddCargoTypeAction())
                .stateEntry(State.GET_CARGO_TYPE_IN_PROCESS, getCargoTypeActionsConfig.startGetCargoTypeAction())
                .stateEntry(State.DELETE_CARGO_TYPE_IN_PROCESS, deleteCargoTypeActionsConfig.startDeleteCargoTypeAction())
                .stateEntry(State.EDIT_CARGO_TYPE_IN_PROCESS, editCargoTypeActionsConfig.startEditCargoTypeAction())
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
                //Edit Cargo
                .withExternal()
                .source(State.INITIAL).target(State.EDIT_CARGO_TYPE_IN_PROCESS)
                .event(Event.EDIT_CARGO_TYPE)
                .and()

                .withExternal()
                .source(State.EDIT_CARGO_TYPE_IN_PROCESS).target(State.EDIT_CARGO_TYPE_ID_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_ID)
                .action(editCargoTypeActionsConfig.inputIdEditCargoTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_CARGO_TYPE_ID_PROVIDED).target(State.EDIT_CARGO_TYPE_NAME_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_NAME)
                .action(editCargoTypeActionsConfig.inputNameEditCargoTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_CARGO_TYPE_NAME_PROVIDED).target(State.EDIT_CARGO_TYPE_REPRESENTATION_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_REPRESENTATION)
                .action(editCargoTypeActionsConfig.inputRepresentationEditCargoTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_CARGO_TYPE_REPRESENTATION_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(editCargoTypeActionsConfig.completeEditCargoTypeAction())
                .and()
                //Add Cargo
                .withExternal()
                .source(State.INITIAL).target(State.ADD_CARGO_TYPE_IN_PROCESS)
                .event(Event.ADD_CARGO_TYPE)
                .and()

                .withExternal()
                .source(State.ADD_CARGO_TYPE_IN_PROCESS).target(State.ADD_CARGO_TYPE_NAME_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_NAME)
                .action(addCargoTypeActionsConfig.inputNameAddCargoTypeAction())
                .and()

                .withExternal()
                .source(State.ADD_CARGO_TYPE_NAME_PROVIDED).target(State.ADD_CARGO_TYPE_REPRESENTATION_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_REPRESENTATION)
                .action(addCargoTypeActionsConfig.inputRepresentationAddCargoTypeAction())
                .and()

                .withExternal()
                .source(State.ADD_CARGO_TYPE_REPRESENTATION_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(addCargoTypeActionsConfig.completeAddCargoTypeAction())
                .and()

                .withExternal()
                .source(State.INITIAL).target(State.DELETE_CARGO_TYPE_IN_PROCESS)
                .event(Event.DELETE_CARGO_TYPE)
                .action(deleteCargoTypeActionsConfig.startDeleteCargoTypeAction())
                .and()
                //Delete Cargo
                .withExternal()
                .source(State.DELETE_CARGO_TYPE_IN_PROCESS).target(State.DELETE_CARGO_TYPE_ID_PROVIDED)
                .event(Event.PROVIDE_CARGO_TYPE_ID)
                .action(deleteCargoTypeActionsConfig.inputIdDeleteCargoTypeAction())
                .and()

                .withExternal()
                .source(State.DELETE_CARGO_TYPE_ID_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(deleteCargoTypeActionsConfig.completeDeleteCargoTypeAction())
                .and()
                //Get Cargo
                .withExternal()
                .source(State.INITIAL).target(State.GET_CARGO_TYPE_IN_PROCESS)
                .event(Event.GET_CARGO_TYPE)
                .and()

                .withExternal()
                .source(State.GET_CARGO_TYPE_IN_PROCESS).target(State.COMPLETED)
                .event(Event.COMPLETE);
    }
}
