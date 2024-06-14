package ru.liga.cargo.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoTypeDto {
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    private String representation;
}
