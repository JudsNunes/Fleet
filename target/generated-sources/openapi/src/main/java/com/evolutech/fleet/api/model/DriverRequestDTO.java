package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DriverRequestDTO
 */

@JsonTypeName("DriverRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class DriverRequestDTO {

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

  private String phone;

  private String email;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthDate;

  private String address;

  public DriverRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DriverRequestDTO(String name, String cpf, String cnhNumber, CnhCategoryEnum cnhCategory, LocalDate cnhExpiryDate) {
    this.name = name;
    this.cpf = cpf;
    this.cnhNumber = cnhNumber;
    this.cnhCategory = cnhCategory;
    this.cnhExpiryDate = cnhExpiryDate;
  }

  public DriverRequestDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DriverRequestDTO cpf(String cpf) {
    this.cpf = cpf;
    return this;
  }

  /**
   * Get cpf
   * @return cpf
  */
  @NotNull 
  @Schema(name = "cpf", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cpf")
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public DriverRequestDTO cnhNumber(String cnhNumber) {
    this.cnhNumber = cnhNumber;
    return this;
  }

  /**
   * Get cnhNumber
   * @return cnhNumber
  */
  @NotNull 
  @Schema(name = "cnhNumber", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cnhNumber")
  public String getCnhNumber() {
    return cnhNumber;
  }

  public void setCnhNumber(String cnhNumber) {
    this.cnhNumber = cnhNumber;
  }

  public DriverRequestDTO cnhCategory(CnhCategoryEnum cnhCategory) {
    this.cnhCategory = cnhCategory;
    return this;
  }

  /**
   * Get cnhCategory
   * @return cnhCategory
  */
  @NotNull 
  @Schema(name = "cnhCategory", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cnhCategory")
  public CnhCategoryEnum getCnhCategory() {
    return cnhCategory;
  }

  public void setCnhCategory(CnhCategoryEnum cnhCategory) {
    this.cnhCategory = cnhCategory;
  }

  public DriverRequestDTO cnhExpiryDate(LocalDate cnhExpiryDate) {
    this.cnhExpiryDate = cnhExpiryDate;
    return this;
  }

  /**
   * Get cnhExpiryDate
   * @return cnhExpiryDate
  */
  @NotNull @Valid 
  @Schema(name = "cnhExpiryDate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cnhExpiryDate")
  public LocalDate getCnhExpiryDate() {
    return cnhExpiryDate;
  }

  public void setCnhExpiryDate(LocalDate cnhExpiryDate) {
    this.cnhExpiryDate = cnhExpiryDate;
  }

  public DriverRequestDTO phone(String phone) {
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

  public DriverRequestDTO email(String email) {
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

  public DriverRequestDTO birthDate(LocalDate birthDate) {
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

  public DriverRequestDTO address(String address) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DriverRequestDTO driverRequest = (DriverRequestDTO) o;
    return Objects.equals(this.name, driverRequest.name) &&
        Objects.equals(this.cpf, driverRequest.cpf) &&
        Objects.equals(this.cnhNumber, driverRequest.cnhNumber) &&
        Objects.equals(this.cnhCategory, driverRequest.cnhCategory) &&
        Objects.equals(this.cnhExpiryDate, driverRequest.cnhExpiryDate) &&
        Objects.equals(this.phone, driverRequest.phone) &&
        Objects.equals(this.email, driverRequest.email) &&
        Objects.equals(this.birthDate, driverRequest.birthDate) &&
        Objects.equals(this.address, driverRequest.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, cpf, cnhNumber, cnhCategory, cnhExpiryDate, phone, email, birthDate, address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DriverRequestDTO {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    cpf: ").append(toIndentedString(cpf)).append("\n");
    sb.append("    cnhNumber: ").append(toIndentedString(cnhNumber)).append("\n");
    sb.append("    cnhCategory: ").append(toIndentedString(cnhCategory)).append("\n");
    sb.append("    cnhExpiryDate: ").append(toIndentedString(cnhExpiryDate)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

