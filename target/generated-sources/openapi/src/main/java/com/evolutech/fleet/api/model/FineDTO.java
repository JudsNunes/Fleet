package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
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
 * FineDTO
 */

@JsonTypeName("Fine")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class FineDTO {

  private UUID id;

  private UUID vehicleId;

  private String driverCpf;

  private String description;

  private Double amount;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate infractionDate;

  private Integer points;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PENDING("PENDING"),
    
    PAID("PAID"),
    
    DISPUTED("DISPUTED");

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

  private String costCenterId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;

  public FineDTO id(UUID id) {
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

  public FineDTO vehicleId(UUID vehicleId) {
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

  public FineDTO driverCpf(String driverCpf) {
    this.driverCpf = driverCpf;
    return this;
  }

  /**
   * Get driverCpf
   * @return driverCpf
  */
  
  @Schema(name = "driverCpf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("driverCpf")
  public String getDriverCpf() {
    return driverCpf;
  }

  public void setDriverCpf(String driverCpf) {
    this.driverCpf = driverCpf;
  }

  public FineDTO description(String description) {
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

  public FineDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public FineDTO infractionDate(LocalDate infractionDate) {
    this.infractionDate = infractionDate;
    return this;
  }

  /**
   * Get infractionDate
   * @return infractionDate
  */
  @Valid 
  @Schema(name = "infractionDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("infractionDate")
  public LocalDate getInfractionDate() {
    return infractionDate;
  }

  public void setInfractionDate(LocalDate infractionDate) {
    this.infractionDate = infractionDate;
  }

  public FineDTO points(Integer points) {
    this.points = points;
    return this;
  }

  /**
   * Get points
   * @return points
  */
  
  @Schema(name = "points", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("points")
  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public FineDTO status(StatusEnum status) {
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

  public FineDTO costCenterId(String costCenterId) {
    this.costCenterId = costCenterId;
    return this;
  }

  /**
   * Get costCenterId
   * @return costCenterId
  */
  
  @Schema(name = "costCenterId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("costCenterId")
  public String getCostCenterId() {
    return costCenterId;
  }

  public void setCostCenterId(String costCenterId) {
    this.costCenterId = costCenterId;
  }

  public FineDTO createdAt(LocalDateTime createdAt) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FineDTO fine = (FineDTO) o;
    return Objects.equals(this.id, fine.id) &&
        Objects.equals(this.vehicleId, fine.vehicleId) &&
        Objects.equals(this.driverCpf, fine.driverCpf) &&
        Objects.equals(this.description, fine.description) &&
        Objects.equals(this.amount, fine.amount) &&
        Objects.equals(this.infractionDate, fine.infractionDate) &&
        Objects.equals(this.points, fine.points) &&
        Objects.equals(this.status, fine.status) &&
        Objects.equals(this.costCenterId, fine.costCenterId) &&
        Objects.equals(this.createdAt, fine.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vehicleId, driverCpf, description, amount, infractionDate, points, status, costCenterId, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FineDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    driverCpf: ").append(toIndentedString(driverCpf)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    infractionDate: ").append(toIndentedString(infractionDate)).append("\n");
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    costCenterId: ").append(toIndentedString(costCenterId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

