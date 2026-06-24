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
 * TripRequestDTO
 */

@JsonTypeName("TripRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class TripRequestDTO {

  private UUID vehicleId;

  private UUID driverId;

  private String description;

  private String origin;

  private String destination;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime departureDate;

  private Double plannedDistanceKm;

  private Double startMileage;

  public TripRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TripRequestDTO(UUID vehicleId, UUID driverId, String description, String origin, String destination, LocalDateTime departureDate) {
    this.vehicleId = vehicleId;
    this.driverId = driverId;
    this.description = description;
    this.origin = origin;
    this.destination = destination;
    this.departureDate = departureDate;
  }

  public TripRequestDTO vehicleId(UUID vehicleId) {
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

  public TripRequestDTO driverId(UUID driverId) {
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

  public TripRequestDTO description(String description) {
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

  public TripRequestDTO origin(String origin) {
    this.origin = origin;
    return this;
  }

  /**
   * Get origin
   * @return origin
  */
  @NotNull 
  @Schema(name = "origin", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("origin")
  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public TripRequestDTO destination(String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   * @return destination
  */
  @NotNull 
  @Schema(name = "destination", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("destination")
  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public TripRequestDTO departureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
    return this;
  }

  /**
   * Get departureDate
   * @return departureDate
  */
  @NotNull @Valid 
  @Schema(name = "departureDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("departureDate")
  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
  }

  public TripRequestDTO plannedDistanceKm(Double plannedDistanceKm) {
    this.plannedDistanceKm = plannedDistanceKm;
    return this;
  }

  /**
   * Get plannedDistanceKm
   * @return plannedDistanceKm
  */
  
  @Schema(name = "plannedDistanceKm", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plannedDistanceKm")
  public Double getPlannedDistanceKm() {
    return plannedDistanceKm;
  }

  public void setPlannedDistanceKm(Double plannedDistanceKm) {
    this.plannedDistanceKm = plannedDistanceKm;
  }

  public TripRequestDTO startMileage(Double startMileage) {
    this.startMileage = startMileage;
    return this;
  }

  /**
   * Get startMileage
   * @return startMileage
  */
  
  @Schema(name = "startMileage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("startMileage")
  public Double getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(Double startMileage) {
    this.startMileage = startMileage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TripRequestDTO tripRequest = (TripRequestDTO) o;
    return Objects.equals(this.vehicleId, tripRequest.vehicleId) &&
        Objects.equals(this.driverId, tripRequest.driverId) &&
        Objects.equals(this.description, tripRequest.description) &&
        Objects.equals(this.origin, tripRequest.origin) &&
        Objects.equals(this.destination, tripRequest.destination) &&
        Objects.equals(this.departureDate, tripRequest.departureDate) &&
        Objects.equals(this.plannedDistanceKm, tripRequest.plannedDistanceKm) &&
        Objects.equals(this.startMileage, tripRequest.startMileage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, driverId, description, origin, destination, departureDate, plannedDistanceKm, startMileage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TripRequestDTO {\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    driverId: ").append(toIndentedString(driverId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    departureDate: ").append(toIndentedString(departureDate)).append("\n");
    sb.append("    plannedDistanceKm: ").append(toIndentedString(plannedDistanceKm)).append("\n");
    sb.append("    startMileage: ").append(toIndentedString(startMileage)).append("\n");
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

