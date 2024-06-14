package ru.liga.bot.mapper;

import org.springframework.stereotype.Component;
import ru.liga.bot.entity.dto.CargoTypeBotDto;
import ru.liga.bot.entity.dto.LoaderBotDto;
import ru.liga.bot.entity.dto.TruckTypeBotDto;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class UpdateMapper {
    public CargoTypeBotDto getCargoTypeBotDtoFromUpdateMessage(String updateText) {
        String id = extractValue(updateText, "-и \"", "\"");
        String name = extractValue(updateText, "-н \"", "\"");
        String representation = extractValue(updateText, "-п \"", "\"");

        return CargoTypeBotDto.builder()
                .id(id != null ? Long.parseLong(id) : null)
                .name(name)
                .representation(representation)
                .build();
    }

    public TruckTypeBotDto getTruckTypeBotDtoFromUpdateMessage(String updateText) {
        TruckTypeBotDto.TruckTypeBotDtoBuilder truckTypeBotDto = TruckTypeBotDto.builder();

        String idPrefix = "-и \"";
        String namePrefix = "-н \"";
        String heightPrefix = "-в \"";
        String widthPrefix = "-ш \"";

        if (updateText.contains(idPrefix)) {
            String id = extractValue(updateText, idPrefix, "\"");
            truckTypeBotDto.id(Long.parseLong(id));
        }

        String name = extractValue(updateText, namePrefix, "\"");
        String height = extractValue(updateText, heightPrefix, "\"");
        String width = extractValue(updateText, widthPrefix, "\"");

        truckTypeBotDto.name(name);
        truckTypeBotDto.width(width != null ? Integer.parseInt(width) : null);
        truckTypeBotDto.height(height != null ? Integer.parseInt(height) : null);

        return truckTypeBotDto.build();
    }

    public LoaderBotDto getLoaderBotDtoFromUpdateMessage(String updateText) {
        LoaderBotDto.LoaderBotDtoBuilder loaderBotDtoBuilder = LoaderBotDto.builder();

        String truckNamePrefix = "-в \"";
        String truckNumberPrefix = "-м \"";
        String algorithmPrefix = "-а \"";
        String cargosPrefix = "-г \"";

        String truckName = extractValue(updateText, truckNamePrefix, "\"");
        String truckNumber = extractValue(updateText, truckNumberPrefix, "\"");
        String algorithm = extractValue(updateText, algorithmPrefix, "\"");
        String cargosRaw = extractValue(updateText, cargosPrefix, "\"");

        loaderBotDtoBuilder.truckName(truckName);
        loaderBotDtoBuilder.truckNumber(truckNumber != null ? Integer.parseInt(truckNumber) : null);
        loaderBotDtoBuilder.algorithmType(algorithm);
        loaderBotDtoBuilder.cargoNames(cargosRaw != null ? Arrays.stream(cargosRaw.split(",")).toList() : new ArrayList<>());

        return loaderBotDtoBuilder.build();
    }

    private String extractValue(String command, String prefix, String suffix) {
        int startIndex = command.indexOf(prefix);
        if (startIndex == -1) {
            return null;
        }
        startIndex += prefix.length();
        int endIndex = command.indexOf(suffix, startIndex);
        if (endIndex == -1) {
            return null;
        }
        return command.substring(startIndex, endIndex);
    }
}
