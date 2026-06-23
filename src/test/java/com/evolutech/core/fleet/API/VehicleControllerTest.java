package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createVehicle_Success() throws Exception {
        VehicleRequestDTO request = VehicleRequestDTO.builder()
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Preto")
                .mileage(5000.0)
                .build();

        VehicleResponseDTO response = VehicleResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Preto")
                .mileage(5000.0)
                .status("ACTIVE")
                .build();

        when(vehicleService.save(any(VehicleRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(jsonPath("$.plate").value("ABC1D23"));
    }

    @Test
    void getVehicleById_Found() throws Exception {
        VehicleResponseDTO response = VehicleResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .build();

        when(vehicleService.findById(anyString())).thenReturn(Optional.of(response));

        mockMvc.perform(get("/vehicles/550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate").value("ABC1D23"));
    }

    @Test
    void getVehicleById_NotFound() throws Exception {
        when(vehicleService.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVehicle_Success() throws Exception {
        VehicleRequestDTO request = VehicleRequestDTO.builder()
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Branco")
                .build();

        VehicleResponseDTO response = VehicleResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Branco")
                .build();

        when(vehicleService.update(anyString(), any(VehicleRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/vehicles/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Branco"));
    }

    @Test
    void deleteVehicle_Success() throws Exception {
        doNothing().when(vehicleService).delete(anyString());

        mockMvc.perform(delete("/vehicles/550e8400-e29b-41d4-a716-446655440000"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createVehicle_InvalidPlate_BadRequest() throws Exception {
        VehicleRequestDTO request = VehicleRequestDTO.builder()
                .plate("invalid")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .build();

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
