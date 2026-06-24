package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ServiceOrderApprovalDTO
 */

@JsonTypeName("ServiceOrderApproval")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class ServiceOrderApprovalDTO {

  private String approvedBy;

  public ServiceOrderApprovalDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ServiceOrderApprovalDTO(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  public ServiceOrderApprovalDTO approvedBy(String approvedBy) {
    this.approvedBy = approvedBy;
    return this;
  }

  /**
   * Get approvedBy
   * @return approvedBy
  */
  @NotNull 
  @Schema(name = "approvedBy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("approvedBy")
  public String getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceOrderApprovalDTO serviceOrderApproval = (ServiceOrderApprovalDTO) o;
    return Objects.equals(this.approvedBy, serviceOrderApproval.approvedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(approvedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceOrderApprovalDTO {\n");
    sb.append("    approvedBy: ").append(toIndentedString(approvedBy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

