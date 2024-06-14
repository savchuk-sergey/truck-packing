package ru.liga.bot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class LoaderBotDto {
    private String truckName;
    private Integer truckNumber;
    private String algorithmType;
    private List<String> cargoNames;
}
