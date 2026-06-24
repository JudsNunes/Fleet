package com.evolutech.fleet.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StartTripRequestDTO
 */

@JsonTypeName("startTrip_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:50:07.496289600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class StartTripRequestDTO {

  private Double startMileage;

  public StartTripRequestDTO startMileage(Double startMileage) {
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
    StartTripRequestDTO startTripRequest = (StartTripRequestDTO) o;
    return Objects.equals(this.startMileage, startTripRequest.startMileage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startMileage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartTripRequestDTO {\n");
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

