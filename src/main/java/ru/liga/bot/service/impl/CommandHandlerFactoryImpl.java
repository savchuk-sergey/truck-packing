package ru.liga.bot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.service.CommandHandlerFactory;
import ru.liga.bot.type.BotCommandType;
import ru.liga.statemachine.service.StateMachineService;

@Component
@Log4j2
@RequiredArgsConstructor
public class CommandHandlerFactoryImpl implements CommandHandlerFactory {
    private final CommandHandler cargoCommandHandler;
    private final CommandHandler truckCommandHandler;
    private final CommandHandler loaderCommandHandler;
    private final CommandHandler startCommandHandler;
    private final CommandHandler defaultCommandHandler;
    private final StateMachineService cargoStateService;
    private final StateMachineService truckStateService;
    private final StateMachineService loaderStateService;

    public CommandHandler getHandler(BotCommandType requestCommandType) {
        BotCommandType.Type commandType = requestCommandType != null
                ? requestCommandType.getType()
                : BotCommandType.Type.IN_PROGRESS;
        log.debug("Request Command Type: {}", commandType);
        if (isCargoCommand(commandType)) {
            return cargoCommandHandler;
        } else if (isTruckCommand(commandType)) {
            return truckCommandHandler;
        } else if (isLoaderCommand(commandType)) {
            return loaderCommandHandler;
        } else if (isStartCommand(commandType)) {
            return startCommandHandler;
        } else {
            return defaultCommandHandler;
        }
    }

    private boolean isCargoCommand(BotCommandType.Type requestCommandtype) {
        return requestCommandtype.equals(BotCommandType.Type.CARGO) ||
                cargoStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.CARGO);
    }

    private boolean isTruckCommand(BotCommandType.Type requestCommandtype) {
        return requestCommandtype.equals(BotCommandType.Type.TRUCK) ||
                truckStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.TRUCK);
    }

    private boolean isLoaderCommand(BotCommandType.Type requestCommandtype) {
        return requestCommandtype.equals(BotCommandType.Type.LOADER) ||
                loaderStateService.getCurrentState().getBotCommandType().getType().equals(BotCommandType.Type.LOADER);
    }

    private boolean isStartCommand(BotCommandType.Type requestCommandtype) {
        return requestCommandtype.equals(BotCommandType.Type.START);
    }
}
