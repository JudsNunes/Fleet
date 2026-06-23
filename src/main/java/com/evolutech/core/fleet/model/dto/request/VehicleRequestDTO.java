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

    private String id;

    @NotBlank(message = "Plate is required")
    @Pattern(regexp = "([A-Z]{3}\\d[A-Z]\\d{2}|[A-Z]{3}-\\d{4})", message = "Plate must be in Mercosul format (ABC1D23) or old format (ABC-1234)")
    private String plate;
    @NotBlank(message = "Model is required")
    private String model;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotNull(message = "Year is required")
    private Integer year;
    private String color;
    private Double mileage;
    @NotBlank(message = "Chassis is required")
    @Pattern(regexp = "[A-HJ-NPR-Z0-9]{17}", message = "Chassis must be 17 characters (VIN format)")
    private String chassis;
    @NotBlank(message = "Renavam is required")
    @Pattern(regexp = "\\d{11}", message = "Renavam must be 11 digits")
    private String renavam;
    @NotBlank(message = "Fuel type is required")
    private String fuelType;
    private Double cargoCapacityKg;
    private Integer passengerCapacity;
    private String engineType;
}
