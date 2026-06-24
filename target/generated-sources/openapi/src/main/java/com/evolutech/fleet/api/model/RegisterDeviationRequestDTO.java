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
 * RegisterDeviationRequestDTO
 */

@JsonTypeName("registerDeviation_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-06-24T10:00:44.889174600-03:00[America/Fortaleza]", comments = "Generator version: 7.5.0")
public class RegisterDeviationRequestDTO {

  private String justification;

  public RegisterDeviationRequestDTO justification(String justification) {
    this.justification = justification;
    return this;
  }

  /**
   * Get justification
   * @return justification
  */
  
  @Schema(name = "justification", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("justification")
  public String getJustification() {
    return justification;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisterDeviationRequestDTO registerDeviationRequest = (RegisterDeviationRequestDTO) o;
    return Objects.equals(this.justification, registerDeviationRequest.justification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(justification);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RegisterDeviationRequestDTO {\n");
    sb.append("    justification: ").append(toIndentedString(justification)).append("\n");
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

