package ru.liga.bot.service.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;
import ru.liga.bot.service.MessageService;
import ru.liga.bot.type.BotCommandType;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_ID_ROW_WIDTH = 10;
    public static final int MAX_WIDTH_ROW_WIDTH = MAX_ID_ROW_WIDTH;
    public static final int MAX_HEIGHT_ROW_WIDTH = MAX_ID_ROW_WIDTH;
    public static final String TRUCK_MARKDOWN_TABLE_ROW = "%-" +
            MAX_ID_ROW_WIDTH + "s %-" +
            MAX_NAME_LENGTH + "s %-" +
            MAX_WIDTH_ROW_WIDTH + "s %-" +
            MAX_HEIGHT_ROW_WIDTH + "s\n";
    public static final int MAX_REPRESENTATION_LENGTH = MAX_NAME_LENGTH;
    public static final String CARGO_MARKDOWN_TABLE_ROW = "%-" +
            MAX_ID_ROW_WIDTH + "s %-" +
            MAX_NAME_LENGTH + "s %-" +
            MAX_REPRESENTATION_LENGTH + "s\n";

    public String getMarkdownTableStringFromCargoTypeDtos(List<CargoTypeBotDto> cargoTypeDtos) {
        StringBuilder table = new StringBuilder();
        table.append("```\n");
        table.append(String.format(CARGO_MARKDOWN_TABLE_ROW, "ID", "NAME", "REPRESENTATION"));
        cargoTypeDtos.forEach(
                cargoTypeDto -> table.append(
                        String.format(
                                CARGO_MARKDOWN_TABLE_ROW,
                                cargoTypeDto.getId(),
                                trimString(cargoTypeDto.getName(), MAX_NAME_LENGTH),
                                trimString(escapeNewLine(cargoTypeDto.getRepresentation()), MAX_REPRESENTATION_LENGTH))
                )
        );
        table.append("```");

        return table.toString();
    }

    public String getMarkdownTableStringFromTruckTypeDtos(List<TruckTypeBotDto> truckTypeDtos) {
        StringBuilder table = new StringBuilder();
        table.append("```\n");
        table.append(String.format(TRUCK_MARKDOWN_TABLE_ROW, "ID", "NAME", "WIDTH", "HEIGHT"));
        truckTypeDtos.forEach(
                truckTypeBotDto -> table.append(
                        String.format(
                                TRUCK_MARKDOWN_TABLE_ROW,
                                truckTypeBotDto.getId(),
                                trimString(truckTypeBotDto.getName(), MAX_NAME_LENGTH),
                                truckTypeBotDto.getWidth(),
                                truckTypeBotDto.getHeight())
                )
        );
        table.append("```");

        return table.toString();
    }

    public SendMessage createNewBotMessage(String message, Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(message)
                .build();
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }

    public BotCommandType getCommandFromBotMessage(String message) {
        return BotCommandType.getByCommand(message.split(" ")[0]);
    }

    public String getMarkdownCodeBlock(String message) {
        return "```\n" + message + "```";
    }

    private String escapeNewLine(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\n", "\\n");
    }

    private String trimString(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        if (input.length() > maxLength) {
            return input.substring(0, maxLength);
        }
        return input;
    }
}
