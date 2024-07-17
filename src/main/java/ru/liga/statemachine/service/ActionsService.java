package ru.liga.statemachine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.statemachine.type.Event;
import ru.liga.statemachine.type.MessageKey;
import ru.liga.statemachine.type.State;
import ru.liga.statemachine.type.Variable;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ActionsService {
    public void sendEvent(StateContext<State, Event> context, Event event) {
        Message<Event> message = MessageBuilder
                .withPayload(event)
                .build();
        context
                .getStateMachine()
                .sendEvent(Mono.just(message))
                .subscribe();
    }

    public void setBotResponseMessageToVariable(StateContext<State, Event> context, String message) {
        context
                .getExtendedState()
                .getVariables()
                .put(Variable.BOT_RESPONSE_MESSAGE, message);
    }

    public String getStringValueFromMessageHeader(Message<Event> message, MessageKey messageKey) {
        Object value = message.getHeaders().get(messageKey.name());
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public int getIntValueFromMessageHeader(Message<Event> message, MessageKey messageKey) {
        Object value = message.getHeaders().get(messageKey.name());
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return 0;
    }

    public Long getLongValueFromMessageHeader(Message<Event> message, MessageKey messageKey) {
        Object value = message.getHeaders().get(messageKey.name());
        if (value instanceof String) {
            return Long.parseLong((String) value);
        }
        return null;
    }

    public List<String> getListStringValueFromMessageHeader(Message<Event> message, MessageKey messageKey) {
        Object value = message.getHeaders().get(messageKey.name());
        if (value instanceof String) {
            return Arrays.stream(((String) value).split(",")).toList();
        }
        return List.of();
    }

    public CargoTypeBotDto.CargoTypeBotDtoBuilder getCargoTypeBotDtoBuilderFromVariables(StateContext<State, Event> context) {
        Object builder = context.getStateMachine()
                .getExtendedState()
                .getVariables()
                .get(Variable.CARGO_TYPE_BOT_DTO_BUILDER);
        if (builder instanceof CargoTypeBotDto.CargoTypeBotDtoBuilder) {
            return (CargoTypeBotDto.CargoTypeBotDtoBuilder) builder;
        }
        return null;
    }

    public TruckTypeBotDto.TruckTypeBotDtoBuilder getTruckTypeBotDtoBuilderFromVariables(StateContext<State, Event> context) {
        Object builder = context.getStateMachine()
                .getExtendedState()
                .getVariables()
                .get(Variable.TRUCK_TYPE_BOT_DTO_BUILDER);
        if (builder instanceof TruckTypeBotDto.TruckTypeBotDtoBuilder) {
            return (TruckTypeBotDto.TruckTypeBotDtoBuilder) builder;
        }
        return null;
    }

    public LoaderBotDto.LoaderBotDtoBuilder getLoaderBotDtoBuilderFromVariables(StateContext<State, Event> context) {
        Object builder = context.getStateMachine()
                .getExtendedState()
                .getVariables()
                .get(Variable.LOADER_BOT_DTO_BUILDER);
        if (builder instanceof LoaderBotDto.LoaderBotDtoBuilder) {
            return (LoaderBotDto.LoaderBotDtoBuilder) builder;
        }
        return null;
    }
}
