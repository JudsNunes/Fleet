package com.evolutech.core.fleet.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderApprovalDTO {

    @NotNull(message = "Approved by is required")
    private String approvedBy;
}
