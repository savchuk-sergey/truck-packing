package ru.liga.bot.type;

import lombok.Getter;

@Getter
public enum BotMessageType {
    CARGO_TYPE_ADDED("Тип груза был успешно добавлен"),
    TRUCK_TYPE_ADDED("Тип кузова был успешно добавлен"),
    CARGO_TYPE_DELETED("Тип груза был успешно удалён"),
    TRUCK_TYPE_DELETED("Тип кузова был успешно удалён"),
    CARGO_TYPE_EDITED("Тип груза был успешно изменён"),
    TRUCK_TYPE_EDITED("Тип кузова был успешно изменён"),
    CARGO_TYPE_NOT_FOUND("Тип груза с id: %s, не найден"),
    TRUCK_TYPE_NOT_FOUND("Тип кузова с id: %s, не найден"),
    CARGO_WRONG_TYPE_MESSAGE_FORMAT("Некорректный формат команды для типа груза"),
    TRUCK_WRONG_TYPE_MESSAGE_FORMAT("Некорректный формат команды для типа кузова"),
    LOADER_WRONG_MESSAGE_FORMAT("Некорректный формат команды для типа кузова"),
    COMMANDS_LIST("Доступные команды:\n %s"),
    WRONG_COMMAND("Некорректная команда: %s"),
    ERROR("Ошибка: %s"),
    CARGO_TYPE_PROVIDE_ID("Введите id груза:"),
    CARGO_TYPE_PROVIDE_NAME("Введите название груза:"),
    CARGO_TYPE_PROVIDE_REPRESENTATION("Введите представление груза:"),
    TRUCK_TYPE_PROVIDE_ID("Введите id грузовика:"),
    TRUCK_TYPE_PROVIDE_NAME("Введите название грузовика:"),
    TRUCK_TYPE_PROVIDE_HEIGHT("Введите высоту грузовика:"),
    TRUCK_TYPE_PROVIDE_WIDTH("Введите ширину грузовика:"),
    LOADER_PROVIDE_TRUCK_TYPE("Введите тип грузовика:"),
    LOADER_PROVIDE_TRUCK_NUMBER("Введите максимальное количество грузовиков:"),
    LOADER_PROVIDE_TYPE("Введите название алгоритма погрузки:"),
    LOADER_PROVIDE_CARGOS("Введите название грузов в следующем формате:Торшер,Велосипед");

    private final String text;

    BotMessageType(String text) {
        this.text = text;
    }
}
