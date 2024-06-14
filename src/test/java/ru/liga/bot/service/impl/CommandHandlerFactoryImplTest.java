package ru.liga.bot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.bot.handler.CommandHandler;
import ru.liga.bot.type.BotCommandType;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommandHandlerFactoryImplTest {

    @Mock
    private CommandHandler cargoCommandHandler;

    @Mock
    private CommandHandler truckCommandHandler;

    @Mock
    private CommandHandler loaderCommandHandler;

    @Mock
    private CommandHandler startCommandHandler;

    @Mock
    private CommandHandler helpCommandHandler;

    @Mock
    private CommandHandler defaultCommandHandler;

    @InjectMocks
    private CommandHandlerFactoryImpl commandHandlerFactory;

    @Test
    public void getHandler_WithCargoType_ReturnsCargoCommandHandler() {
        // Arrange
        BotCommandType.Type type = BotCommandType.Type.CARGO;

        // Act
        CommandHandler result = commandHandlerFactory.getHandler(type);

        // Assert
        assertThat(result).isEqualTo(cargoCommandHandler);
    }

    @Test
    public void getHandler_WithTruckType_ReturnsTruckCommandHandler() {
        // Arrange
        BotCommandType.Type type = BotCommandType.Type.TRUCK;

        // Act
        CommandHandler result = commandHandlerFactory.getHandler(type);

        // Assert
        assertThat(result).isEqualTo(truckCommandHandler);
    }

    // Similar tests for other BotCommandType types

    @Test
    public void getHandler_WithUnknownType_ReturnsDefaultCommandHandler() {
        // Arrange
        BotCommandType.Type type = BotCommandType.Type.DEFAULT;

        // Act
        CommandHandler result = commandHandlerFactory.getHandler(type);

        // Assert
        assertThat(result).isEqualTo(defaultCommandHandler);
    }

    // Add more test cases as needed for different scenarios
}