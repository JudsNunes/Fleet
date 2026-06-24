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
 * DriverDTO
 */

@JsonTypeName("Driver")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class DriverDTO {

  private UUID id;

  private String name;

  private String cpf;

  private String cnhNumber;

  /**
   * Gets or Sets cnhCategory
   */
  public enum CnhCategoryEnum {
    A("A"),
    
    B("B"),
    
    C("C"),
    
    D("D"),
    
    E("E"),
    
    AB("AB"),
    
    AC("AC"),
    
    AD("AD"),
    
    AE("AE");

    private String value;

    CnhCategoryEnum(String value) {
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
    public static CnhCategoryEnum fromValue(String value) {
      for (CnhCategoryEnum b : CnhCategoryEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private CnhCategoryEnum cnhCategory;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate cnhExpiryDate;

  /**
   * Gets or Sets cnhStatus
   */
  public enum CnhStatusEnum {
    ACTIVE("ACTIVE"),
    
    EXPIRED("EXPIRED"),
    
    SUSPENDED("SUSPENDED");

    private String value;

    CnhStatusEnum(String value) {
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
    public static CnhStatusEnum fromValue(String value) {
      for (CnhStatusEnum b : CnhStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private CnhStatusEnum cnhStatus;

  private String phone;

  private String email;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthDate;

  private String address;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    ACTIVE("ACTIVE"),
    
    INACTIVE("INACTIVE"),
    
    SUSPENDED("SUSPENDED");

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

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime updatedAt;

  public DriverDTO id(UUID id) {
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

  public DriverDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DriverDTO cpf(String cpf) {
    this.cpf = cpf;
    return this;
  }

  /**
   * Get cpf
   * @return cpf
  */
  
  @Schema(name = "cpf", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cpf")
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public DriverDTO cnhNumber(String cnhNumber) {
    this.cnhNumber = cnhNumber;
    return this;
  }

  /**
   * Get cnhNumber
   * @return cnhNumber
  */
  
  @Schema(name = "cnhNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cnhNumber")
  public String getCnhNumber() {
    return cnhNumber;
  }

  public void setCnhNumber(String cnhNumber) {
    this.cnhNumber = cnhNumber;
  }

  public DriverDTO cnhCategory(CnhCategoryEnum cnhCategory) {
    this.cnhCategory = cnhCategory;
    return this;
  }

  /**
   * Get cnhCategory
   * @return cnhCategory
  */
  
  @Schema(name = "cnhCategory", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cnhCategory")
  public CnhCategoryEnum getCnhCategory() {
    return cnhCategory;
  }

  public void setCnhCategory(CnhCategoryEnum cnhCategory) {
    this.cnhCategory = cnhCategory;
  }

  public DriverDTO cnhExpiryDate(LocalDate cnhExpiryDate) {
    this.cnhExpiryDate = cnhExpiryDate;
    return this;
  }

  /**
   * Get cnhExpiryDate
   * @return cnhExpiryDate
  */
  @Valid 
  @Schema(name = "cnhExpiryDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cnhExpiryDate")
  public LocalDate getCnhExpiryDate() {
    return cnhExpiryDate;
  }

  public void setCnhExpiryDate(LocalDate cnhExpiryDate) {
    this.cnhExpiryDate = cnhExpiryDate;
  }

  public DriverDTO cnhStatus(CnhStatusEnum cnhStatus) {
    this.cnhStatus = cnhStatus;
    return this;
  }

  /**
   * Get cnhStatus
   * @return cnhStatus
  */
  
  @Schema(name = "cnhStatus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cnhStatus")
  public CnhStatusEnum getCnhStatus() {
    return cnhStatus;
  }

  public void setCnhStatus(CnhStatusEnum cnhStatus) {
    this.cnhStatus = cnhStatus;
  }

  public DriverDTO phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  */
  
  @Schema(name = "phone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public DriverDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public DriverDTO birthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  /**
   * Get birthDate
   * @return birthDate
  */
  @Valid 
  @Schema(name = "birthDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("birthDate")
  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public DriverDTO address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  */
  
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public DriverDTO status(StatusEnum status) {
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

  public DriverDTO createdAt(LocalDateTime createdAt) {
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

  public DriverDTO updatedAt(LocalDateTime updatedAt) {
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
    DriverDTO driver = (DriverDTO) o;
    return Objects.equals(this.id, driver.id) &&
        Objects.equals(this.name, driver.name) &&
        Objects.equals(this.cpf, driver.cpf) &&
        Objects.equals(this.cnhNumber, driver.cnhNumber) &&
        Objects.equals(this.cnhCategory, driver.cnhCategory) &&
        Objects.equals(this.cnhExpiryDate, driver.cnhExpiryDate) &&
        Objects.equals(this.cnhStatus, driver.cnhStatus) &&
        Objects.equals(this.phone, driver.phone) &&
        Objects.equals(this.email, driver.email) &&
        Objects.equals(this.birthDate, driver.birthDate) &&
        Objects.equals(this.address, driver.address) &&
        Objects.equals(this.status, driver.status) &&
        Objects.equals(this.createdAt, driver.createdAt) &&
        Objects.equals(this.updatedAt, driver.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, cpf, cnhNumber, cnhCategory, cnhExpiryDate, cnhStatus, phone, email, birthDate, address, status, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DriverDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    cpf: ").append(toIndentedString(cpf)).append("\n");
    sb.append("    cnhNumber: ").append(toIndentedString(cnhNumber)).append("\n");
    sb.append("    cnhCategory: ").append(toIndentedString(cnhCategory)).append("\n");
    sb.append("    cnhExpiryDate: ").append(toIndentedString(cnhExpiryDate)).append("\n");
    sb.append("    cnhStatus: ").append(toIndentedString(cnhStatus)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

