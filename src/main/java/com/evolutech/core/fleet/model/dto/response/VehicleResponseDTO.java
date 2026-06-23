package com.evolutech.core.fleet.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {

    private String id;
    private String plate;
    private String model;
    private String brand;
    private Integer year;
    private String color;
    private Double mileage;
    private String status;
    private String chassis;
    private String renavam;
    private String fuelType;
    private Double cargoCapacityKg;
    private Integer passengerCapacity;
    private String engineType;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
