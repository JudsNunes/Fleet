package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.service.VehicleService;
import com.evolutech.fleet.api.model.VehicleDTO;
import com.evolutech.fleet.api.model.VehicleRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @MockitoBean
    private VehicleService vehicleService;

    @MockitoBean
    private ApiMapper apiMapper;

    private static final String VEHICLE_ID = "550e8400-e29b-41d4-a716-446655440000";

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createVehicle_Success() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("ABC1D23");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);
        apiRequest.setColor("Preto");
        apiRequest.setChassis("9BWZZZ377VE000001");
        apiRequest.setRenavam("12345678901");
        apiRequest.setFuelType(VehicleRequestDTO.FuelTypeEnum.FLEX);

        com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO internalRequest =
                com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.builder()
                .plate("ABC1D23").model("Corolla").brand("Toyota").year(2023)
                .color("Preto").chassis("9BWZZZ377VE000001").renavam("12345678901").fuelType("FLEX")
                .build();

        VehicleResponseDTO internalResponse = VehicleResponseDTO.builder()
                .id(VEHICLE_ID).plate("ABC1D23").model("Corolla").brand("Toyota").year(2023)
                .color("Preto").chassis("9BWZZZ377VE000001").renavam("12345678901").fuelType("FLEX")
                .status("ACTIVE").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();

        VehicleDTO apiResponse = new VehicleDTO();
        apiResponse.setId(UUID.fromString(VEHICLE_ID));
        apiResponse.setPlate("ABC1D23");
        apiResponse.setModel("Corolla");
        apiResponse.setBrand("Toyota");
        apiResponse.setYear(2023);

        when(apiMapper.toVehicleRequest(any(VehicleRequestDTO.class))).thenReturn(internalRequest);
        when(vehicleService.save(any(com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.class))).thenReturn(internalResponse);
        when(apiMapper.toVehicleApi(any(VehicleResponseDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.plate").value("ABC1D23"));
    }

    @Test
    void getVehicleById_Found() throws Exception {
        VehicleResponseDTO internalResponse = VehicleResponseDTO.builder()
                .id(VEHICLE_ID).plate("ABC1D23").model("Corolla").brand("Toyota").year(2023)
                .build();

        VehicleDTO apiResponse = new VehicleDTO();
        apiResponse.setId(UUID.fromString(VEHICLE_ID));
        apiResponse.setPlate("ABC1D23");

        when(vehicleService.findById(VEHICLE_ID)).thenReturn(Optional.of(internalResponse));
        when(apiMapper.toVehicleApi(any(VehicleResponseDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(get("/vehicles/" + VEHICLE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate").value("ABC1D23"));
    }

    @Test
    void getVehicleById_NotFound() throws Exception {
        when(vehicleService.findById(any(String.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/" + VEHICLE_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVehicle_Success() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("ABC1D23");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);
        apiRequest.setColor("Branco");
        apiRequest.setChassis("9BWZZZ377VE000001");
        apiRequest.setRenavam("12345678901");
        apiRequest.setFuelType(VehicleRequestDTO.FuelTypeEnum.FLEX);

        com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO internalRequest =
                com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.builder()
                .plate("ABC1D23").model("Corolla").brand("Toyota").year(2023)
                .color("Branco").chassis("9BWZZZ377VE000001").renavam("12345678901").fuelType("FLEX")
                .build();

        VehicleResponseDTO internalResponse = VehicleResponseDTO.builder()
                .id(VEHICLE_ID).plate("ABC1D23").color("Branco").build();

        VehicleDTO apiResponse = new VehicleDTO();
        apiResponse.setId(UUID.fromString(VEHICLE_ID));
        apiResponse.setColor("Branco");

        when(apiMapper.toVehicleRequest(any(VehicleRequestDTO.class))).thenReturn(internalRequest);
        when(vehicleService.update(any(String.class), any(com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.class))).thenReturn(internalResponse);
        when(apiMapper.toVehicleApi(any(VehicleResponseDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(put("/vehicles/" + VEHICLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Branco"));
    }

    @Test
    void deleteVehicle_Success() throws Exception {
        doNothing().when(vehicleService).delete(VEHICLE_ID);

        mockMvc.perform(delete("/vehicles/" + VEHICLE_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void createVehicle_InvalidPlate_BadRequest() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("invalid");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createVehicle_MissingChassis_BadRequest() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("ABC1D23");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createVehicle_NullChassis_BadRequest() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("ABC1D23");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);
        apiRequest.setRenavam("12345678901");
        apiRequest.setFuelType(VehicleRequestDTO.FuelTypeEnum.FLEX);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createVehicle_NullRenavam_BadRequest() throws Exception {
        VehicleRequestDTO apiRequest = new VehicleRequestDTO();
        apiRequest.setPlate("ABC1D23");
        apiRequest.setModel("Corolla");
        apiRequest.setBrand("Toyota");
        apiRequest.setYear(2023);
        apiRequest.setChassis("9BWZZZ377VE000001");
        apiRequest.setFuelType(VehicleRequestDTO.FuelTypeEnum.FLEX);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }
}
