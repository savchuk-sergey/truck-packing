package ru.liga.bot.statemachine.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.State;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
class StateMachineConfigTests {
    @Autowired
    StateMachineFactory<State, Event> stateMachineFactory;

    @Test
    void testStateMachine() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        StateMachine<State, Event> stateMachine = stateMachineFactory.getStateMachine(UUID.randomUUID());
        System.out.println("State Machine. State: " + stateMachine.getState());
    }
}