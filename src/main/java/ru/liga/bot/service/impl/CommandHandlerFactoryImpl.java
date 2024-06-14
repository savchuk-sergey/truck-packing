package ru.liga.bot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.CommandHandlerFactory;
import ru.liga.bot.type.BotCommandType;

@Component
public class CommandHandlerFactoryImpl implements CommandHandlerFactory {
    private final CommandHandler cargoCommandHandler;
    private final CommandHandler truckCommandHandler;
    private final CommandHandler loaderCommandHandler;
    private final CommandHandler startCommandHandler;
    private final CommandHandler helpCommandHandler;
    private final CommandHandler defaultCommandHandler;

    @Autowired
    public CommandHandlerFactoryImpl(@Qualifier("cargoCommandHandler") CommandHandler cargoCommandHandler,
                                     @Qualifier("truckCommandHandler") CommandHandler truckCommandHandler,
                                     @Qualifier("loaderCommandHandler") CommandHandler loaderCommandHandler,
                                     @Qualifier("startCommandHandler") CommandHandler startCommandHandler,
                                     @Qualifier("helpCommandHandler") CommandHandler helpCommandHandler,
                                     @Qualifier("defaultCommandHandler") CommandHandler defaultCommandHandler) {
        this.cargoCommandHandler = cargoCommandHandler;
        this.truckCommandHandler = truckCommandHandler;
        this.loaderCommandHandler = loaderCommandHandler;
        this.startCommandHandler = startCommandHandler;
        this.helpCommandHandler = helpCommandHandler;
        this.defaultCommandHandler = defaultCommandHandler;
    }

    public CommandHandler getHandler(BotCommandType.Type type) {
        return switch (type) {
            case CARGO -> cargoCommandHandler;
            case TRUCK -> truckCommandHandler;
            case LOADER -> loaderCommandHandler;
            case START -> startCommandHandler;
            case HELP -> helpCommandHandler;
            default -> defaultCommandHandler;
        };
    }
}
