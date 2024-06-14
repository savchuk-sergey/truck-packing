package ru.liga.bot.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BotCommandType {
    LOAD_TRUCKS(Type.LOADER, "/load_trucks"),
    GET_CARGO_TYPES(Type.CARGO, "/get_cargo_types"),
    ADD_CARGO_TYPE(Type.CARGO, "/add_cargo_type"),
    DELETE_CARGO_TYPE(Type.CARGO, "/delete_cargo_type"),
    EDIT_CARGO_TYPE(Type.CARGO, "/edit_cargo_type"),
    GET_TRUCK_TYPES(Type.TRUCK, "/get_truck_types"),
    ADD_TRUCK_TYPE(Type.TRUCK, "/add_truck_type"),
    DELETE_TRUCK_TYPE(Type.TRUCK, "/delete_truck_type"),
    EDIT_TRUCK_TYPE(Type.TRUCK, "/edit_truck_type"),
    START(Type.START, "/start"),
    HELP(Type.HELP, "/help"),
    DEFAULT(Type.DEFAULT, "");
    private final String command;
    private final Type type;

    BotCommandType(Type type, String command) {
        this.type = type;
        this.command = command;
    }

    public static BotCommandType getByCommand(String command) {
        return Arrays.stream(BotCommandType.values())
                .filter(botCommandType -> botCommandType.command.equals(command))
                .findFirst()
                .orElseThrow();
    }

    public enum Type {
        CARGO,
        TRUCK,
        LOADER,
        DEFAULT,
        START,
        HELP
    }
}
