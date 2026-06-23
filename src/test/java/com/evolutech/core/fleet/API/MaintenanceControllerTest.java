package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.service.MaintenanceService;
import com.evolutech.fleet.api.model.MaintenanceDTO;
import com.evolutech.fleet.api.model.MaintenanceRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaintenanceController.class)
class MaintenanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @MockitoBean
    private MaintenanceService maintenanceService;

    @MockitoBean
    private ApiMapper apiMapper;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createMaintenance_Success() throws Exception {
        MaintenanceRequestDTO apiRequest = new MaintenanceRequestDTO(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Troca de óleo",
                MaintenanceRequestDTO.TypeEnum.MAINTENANCE,
                150.0,
                50000.0,
                60000.0,
                LocalDate.now()
        );

        com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO internalRequest =
                com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO.builder()
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0).mileage(50000.0).nextMileage(60000.0)
                .maintenanceDate(LocalDate.now()).build();

        MaintenanceResponseDTO internalResponse = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo").type("MAINTENANCE")
                .cost(150.0).mileage(50000.0).nextMileage(60000.0)
                .status("PENDING").maintenanceDate(LocalDate.now())
                .createdAt(LocalDateTime.now()).build();

        MaintenanceDTO apiResponse = new MaintenanceDTO();
        apiResponse.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        apiResponse.setDescription("Troca de óleo");
        apiResponse.setType(MaintenanceDTO.TypeEnum.MAINTENANCE);
        apiResponse.setCost(150.0);

        when(apiMapper.toMaintenanceRequest(any(MaintenanceRequestDTO.class))).thenReturn(internalRequest);
        when(maintenanceService.save(any(com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO.class))).thenReturn(internalResponse);
        when(apiMapper.toMaintenanceApi(any(MaintenanceResponseDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/maintenances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Troca de óleo"))
                .andExpect(jsonPath("$.cost").value(150.0));
    }

    @Test
    void getMaintenanceById_Found() throws Exception {
        MaintenanceResponseDTO internalResponse = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .description("Troca de óleo").type("MAINTENANCE")
                .cost(150.0).status("PENDING").build();

        MaintenanceDTO apiResponse = new MaintenanceDTO();
        apiResponse.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        apiResponse.setDescription("Troca de óleo");
        apiResponse.setCost(150.0);

        when(maintenanceService.findById("550e8400-e29b-41d4-a716-446655440001")).thenReturn(internalResponse);
        when(apiMapper.toMaintenanceApi(any(MaintenanceResponseDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(get("/maintenances/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo"));
    }

    @Test
    void updateMaintenance_Success() throws Exception {
        MaintenanceRequestDTO apiRequest = new MaintenanceRequestDTO(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Troca de óleo atualizada",
                MaintenanceRequestDTO.TypeEnum.MAINTENANCE,
                200.0,
                55000.0,
                65000.0,
                LocalDate.now()
        );

        com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO internalRequest =
                com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO.builder()
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo atualizada")
                .type("MAINTENANCE")
                .cost(200.0).mileage(55000.0).nextMileage(65000.0)
                .maintenanceDate(LocalDate.now()).build();

        MaintenanceResponseDTO internalResponse = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .description("Troca de óleo atualizada").build();

        when(apiMapper.toMaintenanceRequest(any(MaintenanceRequestDTO.class))).thenReturn(internalRequest);
        when(maintenanceService.update(any(String.class), any(com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO.class))).thenReturn(internalResponse);

        mockMvc.perform(put("/maintenances/550e8400-e29b-41d4-a716-446655440001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMaintenance_Success() throws Exception {
        doNothing().when(maintenanceService).delete("550e8400-e29b-41d4-a716-446655440001");

        mockMvc.perform(delete("/maintenances/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isNoContent());
    }
}
