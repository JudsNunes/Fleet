package com.evolutech.core.fleet.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManutentionResponseDTO {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manutentionDate;

    private String description;

    private String type;

    private Double cost;

    private Double mileage;

    private Double nextMileage;

    private boolean done;

    private Long vehicleId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;
}

