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
public class FineResponseDTO {

    private String id;
    private String vehicleId;
    private String driverCpf;
    private String description;
    private Double amount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate infractionDate;
    private Integer points;
    private String status;
    private String costCenterId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
