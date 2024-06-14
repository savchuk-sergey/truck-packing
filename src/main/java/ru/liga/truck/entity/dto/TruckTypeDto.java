package ru.liga.truck.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TruckTypeDto {
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    private int width;
    @NotBlank
    private int height;
}
