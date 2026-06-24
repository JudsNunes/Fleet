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
 * VehicleDTO
 */

@JsonTypeName("Vehicle")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class VehicleDTO {

  private UUID id;

  private String plate;

  private String model;

  private String brand;

  private Integer year;

  private String color;

  private Double mileage;

  /**
   * Status do veículo
   */
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    INACTIVE("INACTIVE"),
    
    MAINTENANCE("MAINTENANCE"),
    
    DECOMMISSIONED("DECOMMISSIONED");

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

  private String chassis;

  private String renavam;

  /**
   * Tipo de combustível
   */
  public enum FuelTypeEnum {
    GASOLINE("GASOLINE"),
    
    DIESEL("DIESEL"),
    
    ETHANOL("ETHANOL"),
    
    FLEX("FLEX"),
    
    ELECTRIC("ELECTRIC"),
    
    HYBRID("HYBRID");

    private String value;

    FuelTypeEnum(String value) {
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
    public static FuelTypeEnum fromValue(String value) {
      for (FuelTypeEnum b : FuelTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private FuelTypeEnum fuelType;

  private Double cargoCapacityKg;

  private Integer passengerCapacity;

  private String engineType;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime updatedAt;

  public VehicleDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * UUID único do veículo
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "UUID único do veículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public VehicleDTO plate(String plate) {
    this.plate = plate;
    return this;
  }

  /**
   * Placa do veiculo (Mercosul ou antigo)
   * @return plate
  */
  
  @Schema(name = "plate", example = "ABC1D23", description = "Placa do veiculo (Mercosul ou antigo)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("plate")
  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public VehicleDTO model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Modelo do veículo
   * @return model
  */
  
  @Schema(name = "model", example = "Corolla", description = "Modelo do veículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public VehicleDTO brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Marca do veículo
   * @return brand
  */
  
  @Schema(name = "brand", example = "Toyota", description = "Marca do veículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("brand")
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public VehicleDTO year(Integer year) {
    this.year = year;
    return this;
  }

  /**
   * Ano de fabricação
   * @return year
  */
  
  @Schema(name = "year", example = "2023", description = "Ano de fabricação", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public VehicleDTO color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Cor do veículo
   * @return color
  */
  
  @Schema(name = "color", example = "Preto", description = "Cor do veículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("color")
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public VehicleDTO mileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  /**
   * Quilometragem
   * @return mileage
  */
  
  @Schema(name = "mileage", example = "5000.0", description = "Quilometragem", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mileage")
  public Double getMileage() {
    return mileage;
  }

  public void setMileage(Double mileage) {
    this.mileage = mileage;
  }

  public VehicleDTO status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Status do veículo
   * @return status
  */
  
  @Schema(name = "status", description = "Status do veículo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public VehicleDTO chassis(String chassis) {
    this.chassis = chassis;
    return this;
  }

  /**
   * Número do chassi (VIN) - 17 caracteres
   * @return chassis
  */
  
  @Schema(name = "chassis", example = "9BWZZZ377VE000001", description = "Número do chassi (VIN) - 17 caracteres", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("chassis")
  public String getChassis() {
    return chassis;
  }

  public void setChassis(String chassis) {
    this.chassis = chassis;
  }

  public VehicleDTO renavam(String renavam) {
    this.renavam = renavam;
    return this;
  }

  /**
   * Registro Nacional de Veículos Automotores - 11 dígitos
   * @return renavam
  */
  
  @Schema(name = "renavam", example = "12345678901", description = "Registro Nacional de Veículos Automotores - 11 dígitos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("renavam")
  public String getRenavam() {
    return renavam;
  }

  public void setRenavam(String renavam) {
    this.renavam = renavam;
  }

  public VehicleDTO fuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
    return this;
  }

  /**
   * Tipo de combustível
   * @return fuelType
  */
  
  @Schema(name = "fuelType", description = "Tipo de combustível", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fuelType")
  public FuelTypeEnum getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
  }

  public VehicleDTO cargoCapacityKg(Double cargoCapacityKg) {
    this.cargoCapacityKg = cargoCapacityKg;
    return this;
  }

  /**
   * Capacidade de carga em kg
   * @return cargoCapacityKg
  */
  
  @Schema(name = "cargoCapacityKg", example = "500.0", description = "Capacidade de carga em kg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cargoCapacityKg")
  public Double getCargoCapacityKg() {
    return cargoCapacityKg;
  }

  public void setCargoCapacityKg(Double cargoCapacityKg) {
    this.cargoCapacityKg = cargoCapacityKg;
  }

  public VehicleDTO passengerCapacity(Integer passengerCapacity) {
    this.passengerCapacity = passengerCapacity;
    return this;
  }

  /**
   * Capacidade de passageiros
   * @return passengerCapacity
  */
  
  @Schema(name = "passengerCapacity", example = "5", description = "Capacidade de passageiros", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("passengerCapacity")
  public Integer getPassengerCapacity() {
    return passengerCapacity;
  }

  public void setPassengerCapacity(Integer passengerCapacity) {
    this.passengerCapacity = passengerCapacity;
  }

  public VehicleDTO engineType(String engineType) {
    this.engineType = engineType;
    return this;
  }

  /**
   * Tipo do motor (ex: 1.0, 1.8, 2.0, EV)
   * @return engineType
  */
  
  @Schema(name = "engineType", example = "2.0", description = "Tipo do motor (ex: 1.0, 1.8, 2.0, EV)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("engineType")
  public String getEngineType() {
    return engineType;
  }

  public void setEngineType(String engineType) {
    this.engineType = engineType;
  }

  public VehicleDTO createdAt(LocalDateTime createdAt) {
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

  public VehicleDTO updatedAt(LocalDateTime updatedAt) {
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
    VehicleDTO vehicle = (VehicleDTO) o;
    return Objects.equals(this.id, vehicle.id) &&
        Objects.equals(this.plate, vehicle.plate) &&
        Objects.equals(this.model, vehicle.model) &&
        Objects.equals(this.brand, vehicle.brand) &&
        Objects.equals(this.year, vehicle.year) &&
        Objects.equals(this.color, vehicle.color) &&
        Objects.equals(this.mileage, vehicle.mileage) &&
        Objects.equals(this.status, vehicle.status) &&
        Objects.equals(this.chassis, vehicle.chassis) &&
        Objects.equals(this.renavam, vehicle.renavam) &&
        Objects.equals(this.fuelType, vehicle.fuelType) &&
        Objects.equals(this.cargoCapacityKg, vehicle.cargoCapacityKg) &&
        Objects.equals(this.passengerCapacity, vehicle.passengerCapacity) &&
        Objects.equals(this.engineType, vehicle.engineType) &&
        Objects.equals(this.createdAt, vehicle.createdAt) &&
        Objects.equals(this.updatedAt, vehicle.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, plate, model, brand, year, color, mileage, status, chassis, renavam, fuelType, cargoCapacityKg, passengerCapacity, engineType, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    plate: ").append(toIndentedString(plate)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    mileage: ").append(toIndentedString(mileage)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    chassis: ").append(toIndentedString(chassis)).append("\n");
    sb.append("    renavam: ").append(toIndentedString(renavam)).append("\n");
    sb.append("    fuelType: ").append(toIndentedString(fuelType)).append("\n");
    sb.append("    cargoCapacityKg: ").append(toIndentedString(cargoCapacityKg)).append("\n");
    sb.append("    passengerCapacity: ").append(toIndentedString(passengerCapacity)).append("\n");
    sb.append("    engineType: ").append(toIndentedString(engineType)).append("\n");
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

