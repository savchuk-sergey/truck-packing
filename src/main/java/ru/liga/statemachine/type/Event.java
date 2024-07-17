package ru.liga.statemachine.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.liga.bot.type.BotCommandType;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Event {
    ADD_CARGO_TYPE(BotCommandType.ADD_CARGO_TYPE),
    DELETE_CARGO_TYPE(BotCommandType.DELETE_CARGO_TYPE),
    GET_CARGO_TYPE(BotCommandType.GET_CARGO_TYPES),
    PROVIDE_CARGO_TYPE_ID(BotCommandType.DEFAULT),
    PROVIDE_CARGO_TYPE_NAME(BotCommandType.DEFAULT),
    PROVIDE_CARGO_TYPE_REPRESENTATION(BotCommandType.DEFAULT),
    EDIT_CARGO_TYPE(BotCommandType.EDIT_CARGO_TYPE),
    EDIT_TRUCK_TYPE(BotCommandType.EDIT_TRUCK_TYPE),
    ADD_TRUCK_TYPE(BotCommandType.ADD_TRUCK_TYPE),
    DELETE_TRUCK_TYPE(BotCommandType.DELETE_TRUCK_TYPE),
    GET_TRUCK_TYPE(BotCommandType.GET_TRUCK_TYPES),
    LOAD_TRUCKS(BotCommandType.LOAD_TRUCKS),
    PROVIDE_TRUCK_TYPE_ID(BotCommandType.DEFAULT),
    PROVIDE_TRUCK_TYPE_NAME(BotCommandType.DEFAULT),
    PROVIDE_TRUCK_HEIGHT(BotCommandType.DEFAULT),
    PROVIDE_TRUCK_WIDTH(BotCommandType.DEFAULT),
    PROVIDE_TRUCK_TYPE(BotCommandType.DEFAULT),
    PROVIDE_TRUCK_COUNT(BotCommandType.DEFAULT),
    PROVIDE_LOADER_TYPE(BotCommandType.DEFAULT),
    PROVIDE_CARGOS(BotCommandType.DEFAULT),
    COMPLETE(BotCommandType.DEFAULT),
    CANCEL(BotCommandType.DEFAULT),
    DEFAULT(BotCommandType.DEFAULT),
    RESET(BotCommandType.DEFAULT);

    private final BotCommandType botCommandType;

    public static Event getEventByCommand(BotCommandType commandType) {
        return Arrays.stream(Event.values())
                .filter(event -> event.botCommandType.equals(commandType))
                .findFirst()
                .orElse(DEFAULT);
    }
}
