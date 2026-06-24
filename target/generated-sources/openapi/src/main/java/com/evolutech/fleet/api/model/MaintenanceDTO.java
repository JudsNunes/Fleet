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
 * MaintenanceDTO
 */

@JsonTypeName("Maintenance")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class MaintenanceDTO {

  private UUID id;

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

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PENDING("PENDING"),
    
    IN_PROGRESS("IN_PROGRESS"),
    
    COMPLETED("COMPLETED"),
    
    OVERDUE("OVERDUE");

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

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate maintenanceDate;

  private UUID serviceOrderId;

  private String costCenterId;

  private String projectId;

  /**
   * Tipo de combustível da nota fiscal
   */
  public enum InvoiceFuelTypeEnum {
    GASOLINE("GASOLINE"),
    
    DIESEL("DIESEL"),
    
    ETHANOL("ETHANOL"),
    
    FLEX("FLEX"),
    
    ELECTRIC("ELECTRIC"),
    
    HYBRID("HYBRID");

    private String value;

    InvoiceFuelTypeEnum(String value) {
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
    public static InvoiceFuelTypeEnum fromValue(String value) {
      for (InvoiceFuelTypeEnum b : InvoiceFuelTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private InvoiceFuelTypeEnum invoiceFuelType;

  private Double litersFilled;

  private Double distanceTraveled;

  private Boolean anomalousConsumption;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime updatedAt;

  public MaintenanceDTO id(UUID id) {
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

  public MaintenanceDTO vehicleId(UUID vehicleId) {
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

  public MaintenanceDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "Troca de óleo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MaintenanceDTO type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public MaintenanceDTO cost(Double cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Get cost
   * @return cost
  */
  
  @Schema(name = "cost", example = "150.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cost")
  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public MaintenanceDTO mileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  /**
   * Quilometragem atual
   * @return mileage
  */
  
  @Schema(name = "mileage", description = "Quilometragem atual", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mileage")
  public Double getMileage() {
    return mileage;
  }

  public void setMileage(Double mileage) {
    this.mileage = mileage;
  }

  public MaintenanceDTO nextMileage(Double nextMileage) {
    this.nextMileage = nextMileage;
    return this;
  }

  /**
   * Próxima manutenção
   * @return nextMileage
  */
  
  @Schema(name = "nextMileage", description = "Próxima manutenção", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nextMileage")
  public Double getNextMileage() {
    return nextMileage;
  }

  public void setNextMileage(Double nextMileage) {
    this.nextMileage = nextMileage;
  }

  public MaintenanceDTO status(StatusEnum status) {
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

  public MaintenanceDTO maintenanceDate(LocalDate maintenanceDate) {
    this.maintenanceDate = maintenanceDate;
    return this;
  }

  /**
   * Get maintenanceDate
   * @return maintenanceDate
  */
  @Valid 
  @Schema(name = "maintenanceDate", example = "Sun Jan 14 21:00:00 GMT-03:00 2024", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maintenanceDate")
  public LocalDate getMaintenanceDate() {
    return maintenanceDate;
  }

  public void setMaintenanceDate(LocalDate maintenanceDate) {
    this.maintenanceDate = maintenanceDate;
  }

  public MaintenanceDTO serviceOrderId(UUID serviceOrderId) {
    this.serviceOrderId = serviceOrderId;
    return this;
  }

  /**
   * ID da Ordem de Serviço vinculada
   * @return serviceOrderId
  */
  @Valid 
  @Schema(name = "serviceOrderId", description = "ID da Ordem de Serviço vinculada", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("serviceOrderId")
  public UUID getServiceOrderId() {
    return serviceOrderId;
  }

  public void setServiceOrderId(UUID serviceOrderId) {
    this.serviceOrderId = serviceOrderId;
  }

  public MaintenanceDTO costCenterId(String costCenterId) {
    this.costCenterId = costCenterId;
    return this;
  }

  /**
   * ID do centro de custo
   * @return costCenterId
  */
  
  @Schema(name = "costCenterId", description = "ID do centro de custo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("costCenterId")
  public String getCostCenterId() {
    return costCenterId;
  }

  public void setCostCenterId(String costCenterId) {
    this.costCenterId = costCenterId;
  }

  public MaintenanceDTO projectId(String projectId) {
    this.projectId = projectId;
    return this;
  }

  /**
   * ID do projeto
   * @return projectId
  */
  
  @Schema(name = "projectId", description = "ID do projeto", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("projectId")
  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public MaintenanceDTO invoiceFuelType(InvoiceFuelTypeEnum invoiceFuelType) {
    this.invoiceFuelType = invoiceFuelType;
    return this;
  }

  /**
   * Tipo de combustível da nota fiscal
   * @return invoiceFuelType
  */
  
  @Schema(name = "invoiceFuelType", description = "Tipo de combustível da nota fiscal", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("invoiceFuelType")
  public InvoiceFuelTypeEnum getInvoiceFuelType() {
    return invoiceFuelType;
  }

  public void setInvoiceFuelType(InvoiceFuelTypeEnum invoiceFuelType) {
    this.invoiceFuelType = invoiceFuelType;
  }

  public MaintenanceDTO litersFilled(Double litersFilled) {
    this.litersFilled = litersFilled;
    return this;
  }

  /**
   * Litros abastecidos
   * @return litersFilled
  */
  
  @Schema(name = "litersFilled", description = "Litros abastecidos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("litersFilled")
  public Double getLitersFilled() {
    return litersFilled;
  }

  public void setLitersFilled(Double litersFilled) {
    this.litersFilled = litersFilled;
  }

  public MaintenanceDTO distanceTraveled(Double distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
    return this;
  }

  /**
   * Distância percorrida (km)
   * @return distanceTraveled
  */
  
  @Schema(name = "distanceTraveled", description = "Distância percorrida (km)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("distanceTraveled")
  public Double getDistanceTraveled() {
    return distanceTraveled;
  }

  public void setDistanceTraveled(Double distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }

  public MaintenanceDTO anomalousConsumption(Boolean anomalousConsumption) {
    this.anomalousConsumption = anomalousConsumption;
    return this;
  }

  /**
   * Flag de consumo anômalo
   * @return anomalousConsumption
  */
  
  @Schema(name = "anomalousConsumption", description = "Flag de consumo anômalo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("anomalousConsumption")
  public Boolean getAnomalousConsumption() {
    return anomalousConsumption;
  }

  public void setAnomalousConsumption(Boolean anomalousConsumption) {
    this.anomalousConsumption = anomalousConsumption;
  }

  public MaintenanceDTO createdAt(LocalDateTime createdAt) {
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

  public MaintenanceDTO updatedAt(LocalDateTime updatedAt) {
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
    MaintenanceDTO maintenance = (MaintenanceDTO) o;
    return Objects.equals(this.id, maintenance.id) &&
        Objects.equals(this.vehicleId, maintenance.vehicleId) &&
        Objects.equals(this.description, maintenance.description) &&
        Objects.equals(this.type, maintenance.type) &&
        Objects.equals(this.cost, maintenance.cost) &&
        Objects.equals(this.mileage, maintenance.mileage) &&
        Objects.equals(this.nextMileage, maintenance.nextMileage) &&
        Objects.equals(this.status, maintenance.status) &&
        Objects.equals(this.maintenanceDate, maintenance.maintenanceDate) &&
        Objects.equals(this.serviceOrderId, maintenance.serviceOrderId) &&
        Objects.equals(this.costCenterId, maintenance.costCenterId) &&
        Objects.equals(this.projectId, maintenance.projectId) &&
        Objects.equals(this.invoiceFuelType, maintenance.invoiceFuelType) &&
        Objects.equals(this.litersFilled, maintenance.litersFilled) &&
        Objects.equals(this.distanceTraveled, maintenance.distanceTraveled) &&
        Objects.equals(this.anomalousConsumption, maintenance.anomalousConsumption) &&
        Objects.equals(this.createdAt, maintenance.createdAt) &&
        Objects.equals(this.updatedAt, maintenance.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, vehicleId, description, type, cost, mileage, nextMileage, status, maintenanceDate, serviceOrderId, costCenterId, projectId, invoiceFuelType, litersFilled, distanceTraveled, anomalousConsumption, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MaintenanceDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    vehicleId: ").append(toIndentedString(vehicleId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    mileage: ").append(toIndentedString(mileage)).append("\n");
    sb.append("    nextMileage: ").append(toIndentedString(nextMileage)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    maintenanceDate: ").append(toIndentedString(maintenanceDate)).append("\n");
    sb.append("    serviceOrderId: ").append(toIndentedString(serviceOrderId)).append("\n");
    sb.append("    costCenterId: ").append(toIndentedString(costCenterId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    invoiceFuelType: ").append(toIndentedString(invoiceFuelType)).append("\n");
    sb.append("    litersFilled: ").append(toIndentedString(litersFilled)).append("\n");
    sb.append("    distanceTraveled: ").append(toIndentedString(distanceTraveled)).append("\n");
    sb.append("    anomalousConsumption: ").append(toIndentedString(anomalousConsumption)).append("\n");
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

