package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.service.MaintenanceService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createMaintenance_Success() throws Exception {
        MaintenanceRequestDTO request = MaintenanceRequestDTO.builder()
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0)
                .mileage(50000.0)
                .nextMileage(60000.0)
                .maintenanceDate(LocalDate.now())
                .build();

        MaintenanceResponseDTO response = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0)
                .mileage(50000.0)
                .nextMileage(60000.0)
                .status("PENDING")
                .maintenanceDate(LocalDate.now())
                .build();

        when(maintenanceService.save(any(MaintenanceRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/maintenances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Troca de óleo"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getMaintenanceById_Found() throws Exception {
        MaintenanceResponseDTO response = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0)
                .build();

        when(maintenanceService.findById(anyString())).thenReturn(response);

        mockMvc.perform(get("/maintenances/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo"));
    }

    @Test
    void updateMaintenance_Success() throws Exception {
        MaintenanceRequestDTO request = MaintenanceRequestDTO.builder()
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .description("Troca de óleo atualizada")
                .type("MAINTENANCE")
                .cost(200.0)
                .mileage(55000.0)
                .nextMileage(65000.0)
                .maintenanceDate(LocalDate.now())
                .build();

        MaintenanceResponseDTO response = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .description("Troca de óleo atualizada")
                .type("MAINTENANCE")
                .cost(200.0)
                .build();

        when(maintenanceService.update(anyString(), any(MaintenanceRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/maintenances/550e8400-e29b-41d4-a716-446655440001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo atualizada"));
    }

    @Test
    void deleteMaintenance_Success() throws Exception {
        doNothing().when(maintenanceService).delete(anyString());

        mockMvc.perform(delete("/maintenances/550e8400-e29b-41d4-a716-446655440001"))
                .andExpect(status().isNoContent());
    }
}
