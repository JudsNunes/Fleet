package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
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
 * VehicleAssignmentRequestDTO
 */

@JsonTypeName("VehicleAssignmentRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class VehicleAssignmentRequestDTO {

  private UUID vehicleId;

  private UUID driverId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;

  private String assignedBy;

  private String notes;

  public VehicleAssignmentRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VehicleAssignmentRequestDTO(UUID vehicleId, UUID driverId, LocalDate startDate, String assignedBy) {
    this.vehicleId = vehicleId;
    this.driverId = driverId;
    this.startDate = startDate;
    this.assignedBy = assignedBy;
  }

  public VehicleAssignmentRequestDTO vehicleId(UUID vehicleId) {
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

  public VehicleAssignmentRequestDTO driverId(UUID driverId) {
    this.driverId = driverId;
    return this;
  }

  /**
   * Get driverId
   * @return driverId
  */
  @NotNull @Valid 
  @Schema(name = "driverId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("driverId")
  public UUID getDriverId() {
    return driverId;
  }

  public void setDriverId(UUID driverId) {
    this.driverId = driverId;
  }

  public VehicleAssignmentRequestDTO startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
  */
  @NotNull @Valid 
  @Schema(name = "startDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("startDate")
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public VehicleAssignmentRequestDTO endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
  */
  @Valid 
  @Schema(name = "endDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endDate")
  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public VehicleAssignmentRequestDTO assignedBy(String assignedBy) {
    this.assignedBy = assignedBy;
    return this;
  }

  /**
   * Get assignedBy
   * @return assignedBy
  */
  @NotNull 
  @Schema(name = "assignedBy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("assignedBy")
  public String getAssignedBy() {
    return assignedBy;
  }

  public void setAssignedBy(String assignedBy) {
    this.assignedBy = assignedBy;
  }

  public VehicleAssignmentRequestDTO notes(String notes) {
    this.notes = notes;
    return this;
  }

  /**
   * Get notes
   * @return notes
  */
  
  @Schema(name = "notes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleAssignmentRequestDTO vehicleAssignmentRequest = (VehicleAssignmentRequestDTO) o;
    return Objects.equals(this.vehicleId, vehicleAssignmentRequest.vehicleId) &&
        Objects.equals(this.driverId, vehicleAssignmentRequest.driverId) &&
        Objects.equals(this.startDate, vehicleAssignmentRequest.startDate) &&
        Objects.equals(this.endDate, vehicleAssignmentRequest.endDate) &&
        Objects.equals(this.assignedBy, vehicleAssignmentRequest.assignedBy) &&
        Objects.equals(this.notes, vehicleAssignmentRequest.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, driverId, startDate, endDate, assignedBy, notes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleAssignmentRequestDTO {\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    driverId: ").append(toIndentedString(driverId)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    assignedBy: ").append(toIndentedString(assignedBy)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
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

