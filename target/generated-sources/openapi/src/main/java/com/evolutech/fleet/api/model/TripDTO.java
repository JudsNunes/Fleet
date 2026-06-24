package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * TripDTO
 */

@JsonTypeName("Trip")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class TripDTO {

  private UUID id;

  private UUID vehicleId;

  private UUID driverId;

  private String description;

  private String origin;

  private String destination;

  private Double plannedDistanceKm;

  private Double actualDistanceKm;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime departureDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime arrivalDate;

  private Double startMileage;

  private Double endMileage;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PLANNED("PLANNED"),
    
    IN_PROGRESS("IN_PROGRESS"),
    
    COMPLETED("COMPLETED"),
    
    CANCELLED("CANCELLED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  private Boolean routeDeviation;

  private String deviationJustification;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime updatedAt;

  public TripDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TripDTO vehicleId(UUID vehicleId) {
    this.vehicleId = vehicleId;
    return this;
  }

  /**
   * Get vehicleId
   * @return vehicleId
  */
  @Valid 
  @Schema(name = "vehicleId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vehicleId")
  public UUID getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(UUID vehicleId) {
    this.vehicleId = vehicleId;
  }

  public TripDTO driverId(UUID driverId) {
    this.driverId = driverId;
    return this;
  }

  /**
   * Get driverId
   * @return driverId
  */
  @Valid 
  @Schema(name = "driverId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("driverId")
  public UUID getDriverId() {
    return driverId;
  }

  public void setDriverId(UUID driverId) {
    this.driverId = driverId;
  }

  public TripDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TripDTO origin(String origin) {
    this.origin = origin;
    return this;
  }

  /**
   * Get origin
   * @return origin
  */
  
  @Schema(name = "origin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("origin")
  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public TripDTO destination(String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Get destination
   * @return destination
  */
  
  @Schema(name = "destination", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destination")
  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public TripDTO plannedDistanceKm(Double plannedDistanceKm) {
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

  public TripDTO actualDistanceKm(Double actualDistanceKm) {
    this.actualDistanceKm = actualDistanceKm;
    return this;
  }

  /**
   * Get actualDistanceKm
   * @return actualDistanceKm
  */
  
  @Schema(name = "actualDistanceKm", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("actualDistanceKm")
  public Double getActualDistanceKm() {
    return actualDistanceKm;
  }

  public void setActualDistanceKm(Double actualDistanceKm) {
    this.actualDistanceKm = actualDistanceKm;
  }

  public TripDTO departureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
    return this;
  }

  /**
   * Get departureDate
   * @return departureDate
  */
  @Valid 
  @Schema(name = "departureDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("departureDate")
  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
  }

  public TripDTO arrivalDate(LocalDateTime arrivalDate) {
    this.arrivalDate = arrivalDate;
    return this;
  }

  /**
   * Get arrivalDate
   * @return arrivalDate
  */
  @Valid 
  @Schema(name = "arrivalDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("arrivalDate")
  public LocalDateTime getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDateTime arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public TripDTO startMileage(Double startMileage) {
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

  public TripDTO endMileage(Double endMileage) {
    this.endMileage = endMileage;
    return this;
  }

  /**
   * Get endMileage
   * @return endMileage
  */
  
  @Schema(name = "endMileage", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("endMileage")
  public Double getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(Double endMileage) {
    this.endMileage = endMileage;
  }

  public TripDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public TripDTO routeDeviation(Boolean routeDeviation) {
    this.routeDeviation = routeDeviation;
    return this;
  }

  /**
   * Get routeDeviation
   * @return routeDeviation
  */
  
  @Schema(name = "routeDeviation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("routeDeviation")
  public Boolean getRouteDeviation() {
    return routeDeviation;
  }

  public void setRouteDeviation(Boolean routeDeviation) {
    this.routeDeviation = routeDeviation;
  }

  public TripDTO deviationJustification(String deviationJustification) {
    this.deviationJustification = deviationJustification;
    return this;
  }

  /**
   * Get deviationJustification
   * @return deviationJustification
  */
  
  @Schema(name = "deviationJustification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deviationJustification")
  public String getDeviationJustification() {
    return deviationJustification;
  }

  public void setDeviationJustification(String deviationJustification) {
    this.deviationJustification = deviationJustification;
  }

  public TripDTO createdAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public TripDTO updatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
  */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TripDTO trip = (TripDTO) o;
    return Objects.equals(this.id, trip.id) &&
        Objects.equals(this.vehicleId, trip.vehicleId) &&
        Objects.equals(this.driverId, trip.driverId) &&
        Objects.equals(this.description, trip.description) &&
        Objects.equals(this.origin, trip.origin) &&
        Objects.equals(this.destination, trip.destination) &&
        Objects.equals(this.plannedDistanceKm, trip.plannedDistanceKm) &&
        Objects.equals(this.actualDistanceKm, trip.actualDistanceKm) &&
        Objects.equals(this.departureDate, trip.departureDate) &&
        Objects.equals(this.arrivalDate, trip.arrivalDate) &&
        Objects.equals(this.startMileage, trip.startMileage) &&
        Objects.equals(this.endMileage, trip.endMileage) &&
        Objects.equals(this.status, trip.status) &&
        Objects.equals(this.routeDeviation, trip.routeDeviation) &&
        Objects.equals(this.deviationJustification, trip.deviationJustification) &&
        Objects.equals(this.createdAt, trip.createdAt) &&
        Objects.equals(this.updatedAt, trip.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vehicleId, driverId, description, origin, destination, plannedDistanceKm, actualDistanceKm, departureDate, arrivalDate, startMileage, endMileage, status, routeDeviation, deviationJustification, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TripDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    driverId: ").append(toIndentedString(driverId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    plannedDistanceKm: ").append(toIndentedString(plannedDistanceKm)).append("\n");
    sb.append("    actualDistanceKm: ").append(toIndentedString(actualDistanceKm)).append("\n");
    sb.append("    departureDate: ").append(toIndentedString(departureDate)).append("\n");
    sb.append("    arrivalDate: ").append(toIndentedString(arrivalDate)).append("\n");
    sb.append("    startMileage: ").append(toIndentedString(startMileage)).append("\n");
    sb.append("    endMileage: ").append(toIndentedString(endMileage)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    routeDeviation: ").append(toIndentedString(routeDeviation)).append("\n");
    sb.append("    deviationJustification: ").append(toIndentedString(deviationJustification)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

