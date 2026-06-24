package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
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
 * FineRequestDTO
 */

@JsonTypeName("FineRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class FineRequestDTO {

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

  public FineRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FineRequestDTO(UUID vehicleId, String driverCpf, String description, Double amount, LocalDate infractionDate, StatusEnum status) {
    this.vehicleId = vehicleId;
    this.driverCpf = driverCpf;
    this.description = description;
    this.amount = amount;
    this.infractionDate = infractionDate;
    this.status = status;
  }

  public FineRequestDTO vehicleId(UUID vehicleId) {
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

  public FineRequestDTO driverCpf(String driverCpf) {
    this.driverCpf = driverCpf;
    return this;
  }

  /**
   * Get driverCpf
   * @return driverCpf
  */
  @NotNull 
  @Schema(name = "driverCpf", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("driverCpf")
  public String getDriverCpf() {
    return driverCpf;
  }

  public void setDriverCpf(String driverCpf) {
    this.driverCpf = driverCpf;
  }

  public FineRequestDTO description(String description) {
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

  public FineRequestDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @NotNull 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public FineRequestDTO infractionDate(LocalDate infractionDate) {
    this.infractionDate = infractionDate;
    return this;
  }

  /**
   * Get infractionDate
   * @return infractionDate
  */
  @NotNull @Valid 
  @Schema(name = "infractionDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("infractionDate")
  public LocalDate getInfractionDate() {
    return infractionDate;
  }

  public void setInfractionDate(LocalDate infractionDate) {
    this.infractionDate = infractionDate;
  }

  public FineRequestDTO points(Integer points) {
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

  public FineRequestDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public FineRequestDTO costCenterId(String costCenterId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FineRequestDTO fineRequest = (FineRequestDTO) o;
    return Objects.equals(this.vehicleId, fineRequest.vehicleId) &&
        Objects.equals(this.driverCpf, fineRequest.driverCpf) &&
        Objects.equals(this.description, fineRequest.description) &&
        Objects.equals(this.amount, fineRequest.amount) &&
        Objects.equals(this.infractionDate, fineRequest.infractionDate) &&
        Objects.equals(this.points, fineRequest.points) &&
        Objects.equals(this.status, fineRequest.status) &&
        Objects.equals(this.costCenterId, fineRequest.costCenterId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, driverCpf, description, amount, infractionDate, points, status, costCenterId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FineRequestDTO {\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    driverCpf: ").append(toIndentedString(driverCpf)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    infractionDate: ").append(toIndentedString(infractionDate)).append("\n");
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    costCenterId: ").append(toIndentedString(costCenterId)).append("\n");
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

