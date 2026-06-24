package com.evolutech.core.fleet.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequestDTO {

    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF must be in format XXX.XXX.XXX-XX")
    private String cpf;

    @NotBlank(message = "CNH number is required")
    @Pattern(regexp = "\\d{11}", message = "CNH number must be 11 digits")
    private String cnhNumber;

    @NotBlank(message = "CNH category is required")
    private String cnhCategory;

    @NotNull(message = "CNH expiry date is required")
    private LocalDate cnhExpiryDate;

    private String phone;

    private String email;

    private LocalDate birthDate;

    private String address;
}
