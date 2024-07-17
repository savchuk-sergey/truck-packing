package ru.liga.statemachine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import reactor.core.publisher.Mono;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.State;
import ru.liga.statemachine.type.Variable;

@RequiredArgsConstructor
@Slf4j
public class StateMachineService {
    private final StateMachine<State, Event> stateMachine;

    public void sendEvent(Event commandEvent) {
        log.debug("commandEvent: {}", commandEvent);
        Message<Event> message = MessageBuilder
                .withPayload(commandEvent)
                .build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void sendEventWithHeader(Event commandEvent, Object data) {
        Message<Event> message = MessageBuilder
                .withPayload(commandEvent)
                .setHeader(String.valueOf(MessageKey.DATA), data)
                .build();
        stateMachine.sendEvent(Mono.just(message)).subscribe();
    }

    public void moveToNextStateWithMessage(MessageKey messageKeyType, Object data) {
        log.debug("MessageKey: {}, data: {}, NextEvent: {}, CurrentState: {}", messageKeyType, data, getNextEvent(), getCurrentState());
        this.sendEventWithHeader(getNextEvent(), data);
        if (getNextEvent().equals(Event.COMPLETE)) {
            this.sendEvent(getNextEvent());
        }
    }

    public String getStringVariableValue(Variable variable) {
        return (String) stateMachine.getExtendedState().getVariables().get(variable);
    }

    public State getCurrentState() {
        return stateMachine.getState().getId();
    }

    private Event getNextEvent() {
        return stateMachine.getTransitions().stream()
                .filter(transition -> transition.getSource().getId().equals(getCurrentState()))
                .map(transition -> transition.getTrigger().getEvent())
                .findFirst()
                .orElse(Event.DEFAULT);
    }
}
