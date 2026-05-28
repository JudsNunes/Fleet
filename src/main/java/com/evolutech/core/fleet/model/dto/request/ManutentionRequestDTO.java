package com.evolutech.core.fleet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManutentionRequestDTO {

    private String id;

    @NotNull(message = "Maintenance date is required")
    private LocalDate manutentionDate;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    @NotNull(message = "Cost is required")
    @Positive(message = "Cost must be greater than zero")
    private Double cost;

    @NotNull(message = "Current mileage is required")
    @Positive(message = "Mileage must be greater than zero")
    private Double mileage;

    @NotNull(message = "Next mileage is required")
    @Positive(message = "Next mileage must be greater than zero")
    private Double nextMileage;

    private boolean done;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;
}

