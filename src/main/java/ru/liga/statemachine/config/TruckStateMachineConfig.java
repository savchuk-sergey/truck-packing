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
import ru.liga.statemachine.actions.truck.AddTruckTypeActionsConfig;
import ru.liga.statemachine.actions.truck.DeleteTruckTypeActionsConfig;
import ru.liga.statemachine.actions.truck.EditTruckTypeActionsConfig;
import ru.liga.statemachine.actions.truck.GetTruckTypeActionsConfig;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachine(name = "truckStateMachine")
public class TruckStateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {
    private final AddTruckTypeActionsConfig addTruckTypeActionsConfig;
    private final DeleteTruckTypeActionsConfig deleteTruckTypeActionsConfig;
    private final EditTruckTypeActionsConfig editTruckTypeActionsConfig;
    private final GetTruckTypeActionsConfig getTruckTypeActionsConfig;
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

                .stateEntry(State.ADD_TRUCK_TYPE_IN_PROCESS, addTruckTypeActionsConfig.startAddTruckTypeAction())
                .stateEntry(State.GET_TRUCK_TYPE_IN_PROCESS, getTruckTypeActionsConfig.startGetTruckTypeAction())
                .stateEntry(State.DELETE_TRUCK_TYPE_IN_PROCESS, deleteTruckTypeActionsConfig.startDeleteTruckTypeAction())
                .stateEntry(State.EDIT_TRUCK_TYPE_IN_PROCESS, editTruckTypeActionsConfig.startEditTruckTypeAction())
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
                //Edit Truck
                .withExternal()
                .source(State.INITIAL).target(State.EDIT_TRUCK_TYPE_IN_PROCESS)
                .event(Event.EDIT_TRUCK_TYPE)
                .and()

                .withExternal()
                .source(State.EDIT_TRUCK_TYPE_IN_PROCESS).target(State.EDIT_TRUCK_TYPE_ID_PROVIDED)
                .event(Event.PROVIDE_TRUCK_TYPE_ID)
                .action(editTruckTypeActionsConfig.inputIdEditTruckTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_TRUCK_TYPE_ID_PROVIDED).target(State.EDIT_CARGO_TYPE_NAME_PROVIDED)
                .event(Event.PROVIDE_TRUCK_TYPE_NAME)
                .action(editTruckTypeActionsConfig.inputNameEditTruckTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_CARGO_TYPE_NAME_PROVIDED).target(State.EDIT_TRUCK_HEIGHT_PROVIDED)
                .event(Event.PROVIDE_TRUCK_HEIGHT)
                .action(editTruckTypeActionsConfig.inputHeightEditTruckTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_TRUCK_HEIGHT_PROVIDED).target(State.EDIT_TRUCK_WIDTH_PROVIDED)
                .event(Event.PROVIDE_TRUCK_WIDTH)
                .action(editTruckTypeActionsConfig.inputWidthEditTruckTypeAction())
                .and()

                .withExternal()
                .source(State.EDIT_TRUCK_WIDTH_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(editTruckTypeActionsConfig.completeEditTruckTypeAction())
                .and()
                //Add Truck
                .withExternal()
                .source(State.INITIAL).target(State.ADD_TRUCK_TYPE_IN_PROCESS)
                .event(Event.ADD_TRUCK_TYPE)
                .and()

                .withExternal()
                .source(State.ADD_TRUCK_TYPE_IN_PROCESS).target(State.ADD_TRUCK_TYPE_NAME_PROVIDED)
                .event(Event.PROVIDE_TRUCK_TYPE_NAME)
                .action(addTruckTypeActionsConfig.inputNameAddTruckTypeAction())
                .and()

                .withExternal()
                .source(State.ADD_TRUCK_TYPE_NAME_PROVIDED).target(State.ADD_TRUCK_HEIGHT_PROVIDED)
                .event(Event.PROVIDE_TRUCK_HEIGHT)
                .action(addTruckTypeActionsConfig.inputHeightAddTruckTypeAction())
                .and()

                .withExternal()
                .source(State.ADD_TRUCK_HEIGHT_PROVIDED).target(State.ADD_TRUCK_WIDTH_PROVIDED)
                .event(Event.PROVIDE_TRUCK_WIDTH)
                .action(addTruckTypeActionsConfig.inputWidthAddTruckTypeAction())
                .and()

                .withExternal()
                .source(State.ADD_TRUCK_WIDTH_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(addTruckTypeActionsConfig.completeAddTruckTypeAction())
                .and()
                //Delete Truck
                .withExternal()
                .source(State.INITIAL).target(State.DELETE_TRUCK_TYPE_IN_PROCESS)
                .event(Event.DELETE_TRUCK_TYPE)
                .action(deleteTruckTypeActionsConfig.startDeleteTruckTypeAction())
                .and()

                .withExternal()
                .source(State.DELETE_TRUCK_TYPE_IN_PROCESS).target(State.DELETE_TRUCK_TYPE_ID_PROVIDED)
                .event(Event.PROVIDE_TRUCK_TYPE_ID)
                .action(deleteTruckTypeActionsConfig.inputIdDeleteTruckTypeAction())
                .and()

                .withExternal()
                .source(State.DELETE_TRUCK_TYPE_ID_PROVIDED).target(State.COMPLETED)
                .event(Event.COMPLETE)
                .action(deleteTruckTypeActionsConfig.completeDeleteTruckTypeAction())
                .and()
                //Get Truck
                .withExternal()
                .source(State.INITIAL)
                .target(State.GET_TRUCK_TYPE_IN_PROCESS)
                .event(Event.GET_TRUCK_TYPE)
                .and()

                .withExternal()
                .source(State.GET_TRUCK_TYPE_IN_PROCESS)
                .target(State.COMPLETED)
                .event(Event.COMPLETE);
    }
}
