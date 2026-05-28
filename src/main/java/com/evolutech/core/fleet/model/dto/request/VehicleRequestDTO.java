package com.evolutech.core.fleet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    private Long id;

    @NotBlank(message = "Plate is required")
    @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "Plate must be in the format ABC-1234")
    private String plate;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Year is required")
    private Integer year;

    private String color;

    private Double mileage;
}

