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
 * CompleteTripRequestDTO
 */

@JsonTypeName("completeTrip_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class CompleteTripRequestDTO {

  private Double endMileage;

  public CompleteTripRequestDTO endMileage(Double endMileage) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompleteTripRequestDTO completeTripRequest = (CompleteTripRequestDTO) o;
    return Objects.equals(this.endMileage, completeTripRequest.endMileage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endMileage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompleteTripRequestDTO {\n");
    sb.append("    endMileage: ").append(toIndentedString(endMileage)).append("\n");
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

