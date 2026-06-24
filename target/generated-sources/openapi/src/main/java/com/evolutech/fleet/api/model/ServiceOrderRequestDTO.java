package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ServiceOrderRequestDTO
 */

@JsonTypeName("ServiceOrderRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class ServiceOrderRequestDTO {

  private UUID vehicleId;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime warrantyExpiryDate;

  public ServiceOrderRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ServiceOrderRequestDTO(UUID vehicleId, String description) {
    this.vehicleId = vehicleId;
    this.description = description;
  }

  public ServiceOrderRequestDTO vehicleId(UUID vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * Get vehicleId
   * @return vehicleId
  */
  @NotNull @Valid 
  @Schema(name = "vehicleId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("vehicleId")
  public UUID getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(UUID vehicleId) {
    this.vehicleId = vehicleId;
  }

  public ServiceOrderRequestDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @NotNull 
  @Schema(name = "description", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ServiceOrderRequestDTO warrantyExpiryDate(LocalDateTime warrantyExpiryDate) {
    this.warrantyExpiryDate = warrantyExpiryDate;
    return this;
  }

  /**
   * Get warrantyExpiryDate
   * @return warrantyExpiryDate
  */
  @Valid 
  @Schema(name = "warrantyExpiryDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("warrantyExpiryDate")
  public LocalDateTime getWarrantyExpiryDate() {
    return warrantyExpiryDate;
  }

  public void setWarrantyExpiryDate(LocalDateTime warrantyExpiryDate) {
    this.warrantyExpiryDate = warrantyExpiryDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceOrderRequestDTO serviceOrderRequest = (ServiceOrderRequestDTO) o;
    return Objects.equals(this.vehicleId, serviceOrderRequest.vehicleId) &&
        Objects.equals(this.description, serviceOrderRequest.description) &&
        Objects.equals(this.warrantyExpiryDate, serviceOrderRequest.warrantyExpiryDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, description, warrantyExpiryDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceOrderRequestDTO {\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    warrantyExpiryDate: ").append(toIndentedString(warrantyExpiryDate)).append("\n");
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

