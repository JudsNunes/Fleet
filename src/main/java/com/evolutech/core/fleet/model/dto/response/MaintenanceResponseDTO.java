package com.evolutech.core.fleet.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDTO {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maintenanceDate;

    private String description;

    private String type;

    private Double cost;

    private Double mileage;

    private Double nextMileage;

    private String status;

    private String vehicleId;

    private String invoiceFuelType;

    private Double litersFilled;

    private Double distanceTraveled;

    private Boolean anomalousConsumption;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
