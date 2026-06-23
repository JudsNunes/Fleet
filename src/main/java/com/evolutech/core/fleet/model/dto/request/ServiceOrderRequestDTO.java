package com.evolutech.core.fleet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderRequestDTO {

    private String id;
    @NotBlank(message = "Vehicle ID is required")
    private String vehicleId;
    @NotBlank(message = "Description is required")
    private String description;
    private LocalDateTime warrantyExpiryDate;
}
