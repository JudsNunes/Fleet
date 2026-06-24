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
 * MaintenanceRequestDTO
 */

@JsonTypeName("MaintenanceRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class MaintenanceRequestDTO {

  private UUID vehicleId;

  private String description;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    MAINTENANCE("MAINTENANCE"),
    
    FUEL("FUEL"),
    
    TOLL("TOLL"),
    
    PARKING("PARKING"),
    
    OTHER("OTHER");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private Double cost;

  private Double mileage;

  private Double nextMileage;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate maintenanceDate;

  public MaintenanceRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MaintenanceRequestDTO(UUID vehicleId, String description, TypeEnum type, Double cost, Double mileage, Double nextMileage, LocalDate maintenanceDate) {
    this.vehicleId = vehicleId;
    this.description = description;
    this.type = type;
    this.cost = cost;
    this.mileage = mileage;
    this.nextMileage = nextMileage;
    this.maintenanceDate = maintenanceDate;
  }

  public MaintenanceRequestDTO vehicleId(UUID vehicleId) {
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

  public MaintenanceRequestDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @NotNull 
  @Schema(name = "description", example = "Troca de óleo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MaintenanceRequestDTO type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public MaintenanceRequestDTO cost(Double cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Get cost
   * @return cost
  */
  @NotNull 
  @Schema(name = "cost", example = "150.0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cost")
  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public MaintenanceRequestDTO mileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  /**
   * Get mileage
   * @return mileage
  */
  @NotNull 
  @Schema(name = "mileage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mileage")
  public Double getMileage() {
    return mileage;
  }

  public void setMileage(Double mileage) {
    this.mileage = mileage;
  }

  public MaintenanceRequestDTO nextMileage(Double nextMileage) {
    this.nextMileage = nextMileage;
    return this;
  }

  /**
   * Get nextMileage
   * @return nextMileage
  */
  @NotNull 
  @Schema(name = "nextMileage", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nextMileage")
  public Double getNextMileage() {
    return nextMileage;
  }

  public void setNextMileage(Double nextMileage) {
    this.nextMileage = nextMileage;
  }

  public MaintenanceRequestDTO maintenanceDate(LocalDate maintenanceDate) {
    this.maintenanceDate = maintenanceDate;
    return this;
  }

  /**
   * Get maintenanceDate
   * @return maintenanceDate
  */
  @NotNull @Valid 
  @Schema(name = "maintenanceDate", example = "Sun Jan 14 21:00:00 GMT-03:00 2024", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("maintenanceDate")
  public LocalDate getMaintenanceDate() {
    return maintenanceDate;
  }

  public void setMaintenanceDate(LocalDate maintenanceDate) {
    this.maintenanceDate = maintenanceDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MaintenanceRequestDTO maintenanceRequest = (MaintenanceRequestDTO) o;
    return Objects.equals(this.vehicleId, maintenanceRequest.vehicleId) &&
        Objects.equals(this.description, maintenanceRequest.description) &&
        Objects.equals(this.type, maintenanceRequest.type) &&
        Objects.equals(this.cost, maintenanceRequest.cost) &&
        Objects.equals(this.mileage, maintenanceRequest.mileage) &&
        Objects.equals(this.nextMileage, maintenanceRequest.nextMileage) &&
        Objects.equals(this.maintenanceDate, maintenanceRequest.maintenanceDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleId, description, type, cost, mileage, nextMileage, maintenanceDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaintenanceRequestDTO {\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    mileage: ").append(toIndentedString(mileage)).append("\n");
    sb.append("    nextMileage: ").append(toIndentedString(nextMileage)).append("\n");
    sb.append("    maintenanceDate: ").append(toIndentedString(maintenanceDate)).append("\n");
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

