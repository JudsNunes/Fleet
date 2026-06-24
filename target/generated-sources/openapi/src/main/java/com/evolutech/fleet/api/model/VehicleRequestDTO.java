package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * VehicleRequestDTO
 */

@JsonTypeName("VehicleRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class VehicleRequestDTO {

  private String plate;

  private String model;

  private String brand;

  private Integer year;

  private String color;

  private Double mileage;

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

  public VehicleRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VehicleRequestDTO(String plate, String model, String brand, Integer year, String chassis, String renavam, FuelTypeEnum fuelType) {
    this.plate = plate;
    this.model = model;
    this.brand = brand;
    this.year = year;
    this.chassis = chassis;
    this.renavam = renavam;
    this.fuelType = fuelType;
  }

  public VehicleRequestDTO plate(String plate) {
    this.plate = plate;
    return this;
  }

  /**
   * Placa do veiculo (Mercosul ou antigo)
   * @return plate
  */
  @NotNull 
  @Schema(name = "plate", example = "ABC1D23", description = "Placa do veiculo (Mercosul ou antigo)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("plate")
  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public VehicleRequestDTO model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
  */
  @NotNull 
  @Schema(name = "model", example = "Corolla", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public VehicleRequestDTO brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   * @return brand
  */
  @NotNull 
  @Schema(name = "brand", example = "Toyota", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("brand")
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public VehicleRequestDTO year(Integer year) {
    this.year = year;
    return this;
  }

  /**
   * Get year
   * @return year
  */
  @NotNull 
  @Schema(name = "year", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public VehicleRequestDTO color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
  */
  
  @Schema(name = "color", example = "Preto", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("color")
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public VehicleRequestDTO mileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  /**
   * Get mileage
   * @return mileage
  */
  
  @Schema(name = "mileage", example = "5000.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mileage")
  public Double getMileage() {
    return mileage;
  }

  public void setMileage(Double mileage) {
    this.mileage = mileage;
  }

  public VehicleRequestDTO chassis(String chassis) {
    this.chassis = chassis;
    return this;
  }

  /**
   * Número do chassi (VIN) - 17 caracteres
   * @return chassis
  */
  @NotNull 
  @Schema(name = "chassis", example = "9BWZZZ377VE000001", description = "Número do chassi (VIN) - 17 caracteres", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("chassis")
  public String getChassis() {
    return chassis;
  }

  public void setChassis(String chassis) {
    this.chassis = chassis;
  }

  public VehicleRequestDTO renavam(String renavam) {
    this.renavam = renavam;
    return this;
  }

  /**
   * Registro Nacional de Veículos Automotores - 11 dígitos
   * @return renavam
  */
  @NotNull 
  @Schema(name = "renavam", example = "12345678901", description = "Registro Nacional de Veículos Automotores - 11 dígitos", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("renavam")
  public String getRenavam() {
    return renavam;
  }

  public void setRenavam(String renavam) {
    this.renavam = renavam;
  }

  public VehicleRequestDTO fuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
    return this;
  }

  /**
   * Tipo de combustível
   * @return fuelType
  */
  @NotNull 
  @Schema(name = "fuelType", description = "Tipo de combustível", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fuelType")
  public FuelTypeEnum getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
  }

  public VehicleRequestDTO cargoCapacityKg(Double cargoCapacityKg) {
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

  public VehicleRequestDTO passengerCapacity(Integer passengerCapacity) {
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

  public VehicleRequestDTO engineType(String engineType) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VehicleRequestDTO vehicleRequest = (VehicleRequestDTO) o;
    return Objects.equals(this.plate, vehicleRequest.plate) &&
        Objects.equals(this.model, vehicleRequest.model) &&
        Objects.equals(this.brand, vehicleRequest.brand) &&
        Objects.equals(this.year, vehicleRequest.year) &&
        Objects.equals(this.color, vehicleRequest.color) &&
        Objects.equals(this.mileage, vehicleRequest.mileage) &&
        Objects.equals(this.chassis, vehicleRequest.chassis) &&
        Objects.equals(this.renavam, vehicleRequest.renavam) &&
        Objects.equals(this.fuelType, vehicleRequest.fuelType) &&
        Objects.equals(this.cargoCapacityKg, vehicleRequest.cargoCapacityKg) &&
        Objects.equals(this.passengerCapacity, vehicleRequest.passengerCapacity) &&
        Objects.equals(this.engineType, vehicleRequest.engineType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plate, model, brand, year, color, mileage, chassis, renavam, fuelType, cargoCapacityKg, passengerCapacity, engineType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VehicleRequestDTO {\n");
    sb.append("    plate: ").append(toIndentedString(plate)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    mileage: ").append(toIndentedString(mileage)).append("\n");
    sb.append("    chassis: ").append(toIndentedString(chassis)).append("\n");
    sb.append("    renavam: ").append(toIndentedString(renavam)).append("\n");
    sb.append("    fuelType: ").append(toIndentedString(fuelType)).append("\n");
    sb.append("    cargoCapacityKg: ").append(toIndentedString(cargoCapacityKg)).append("\n");
    sb.append("    passengerCapacity: ").append(toIndentedString(passengerCapacity)).append("\n");
    sb.append("    engineType: ").append(toIndentedString(engineType)).append("\n");
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

