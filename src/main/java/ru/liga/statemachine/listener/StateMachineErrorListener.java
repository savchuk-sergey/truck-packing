package ru.liga.statemachine.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

@Slf4j
public class StateMachineErrorListener extends StateMachineListenerAdapter<State, Event> {
    @Override
    public void transition(Transition<State, Event> transition) {
        log.debug("Transitioning to {}", transition.getTarget().getId());
    }

    @Override
    public void stateMachineError(StateMachine<State, Event> stateMachine, Exception exception) {
        log.debug("Error occurred during state transition: {}", exception.getMessage());
    }

    @Override
    public void eventNotAccepted(Message<Event> event) {
        log.debug("Error with event: {}", event.getPayload());
    }
}