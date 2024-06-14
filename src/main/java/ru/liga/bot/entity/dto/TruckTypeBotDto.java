package ru.liga.bot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TruckTypeBotDto {
    private Long id;
    private String name;
    private Integer width;
    private Integer height;
}
