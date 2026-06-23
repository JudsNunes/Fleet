package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.VehicleMapper;
import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.FuelType;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import com.evolutech.core.fleet.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private VehicleEntity vehicleEntity;
    private VehicleResponseDTO vehicleResponseDTO;
    private VehicleRequestDTO vehicleRequestDTO;

    @BeforeEach
    void setUp() {
        vehicleEntity = new VehicleEntity();
        vehicleEntity.setId("550e8400-e29b-41d4-a716-446655440000");
        vehicleEntity.setPlate("ABC1D23");
        vehicleEntity.setModel("Corolla");
        vehicleEntity.setBrand("Toyota");
        vehicleEntity.setYear(2023);
        vehicleEntity.setColor("Preto");
        vehicleEntity.setMileage(5000.0);
        vehicleEntity.setStatus(VehicleStatus.ACTIVE);
        vehicleEntity.setChassis("9BWZZZ377VE000001");
        vehicleEntity.setRenavam("12345678901");
        vehicleEntity.setFuelType(FuelType.FLEX);
        vehicleEntity.setCargoCapacityKg(500.0);
        vehicleEntity.setPassengerCapacity(5);
        vehicleEntity.setEngineType("2.0");
        vehicleEntity.setCreatedAt(LocalDateTime.now());
        vehicleEntity.setUpdatedAt(LocalDateTime.now());

        vehicleResponseDTO = VehicleResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Preto")
                .mileage(5000.0)
                .status("ACTIVE")
                .chassis("9BWZZZ377VE000001")
                .renavam("12345678901")
                .fuelType("FLEX")
                .cargoCapacityKg(500.0)
                .passengerCapacity(5)
                .engineType("2.0")
                .build();

        vehicleRequestDTO = VehicleRequestDTO.builder()
                .plate("ABC1D23")
                .model("Corolla")
                .brand("Toyota")
                .year(2023)
                .color("Preto")
                .mileage(5000.0)
                .chassis("9BWZZZ377VE000001")
                .renavam("12345678901")
                .fuelType("FLEX")
                .cargoCapacityKg(500.0)
                .passengerCapacity(5)
                .engineType("2.0")
                .build();
    }

    @Test
    void save_Success() {
        when(vehicleRepository.findByPlateAndNotDeleted(anyString())).thenReturn(Optional.empty());
        when(vehicleMapper.toEntity(any(VehicleRequestDTO.class))).thenReturn(vehicleEntity);
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);
        when(vehicleMapper.toResponseDTO(any(VehicleEntity.class))).thenReturn(vehicleResponseDTO);

        var result = vehicleService.save(vehicleRequestDTO);

        assertNotNull(result);
        assertEquals("ABC1D23", result.getPlate());
        verify(vehicleRepository).save(any(VehicleEntity.class));
    }

    @Test
    void save_PlateDuplicated_ThrowsConflictException() {
        when(vehicleRepository.findByPlateAndNotDeleted(anyString())).thenReturn(Optional.of(vehicleEntity));

        assertThrows(ConflictException.class, () -> vehicleService.save(vehicleRequestDTO));
    }

    @Test
    void findById_Found() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toResponseDTO(any(VehicleEntity.class))).thenReturn(vehicleResponseDTO);

        var result = vehicleService.findById("550e8400-e29b-41d4-a716-446655440000");

        assertTrue(result.isPresent());
        assertEquals("ABC1D23", result.get().getPlate());
    }

    @Test
    void findById_NotFound() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        var result = vehicleService.findById("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void update_Success() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);
        when(vehicleMapper.toResponseDTO(any(VehicleEntity.class))).thenReturn(vehicleResponseDTO);

        var result = vehicleService.update("550e8400-e29b-41d4-a716-446655440000", vehicleRequestDTO);

        assertNotNull(result);
        verify(vehicleRepository).save(any(VehicleEntity.class));
    }

    @Test
    void update_NotFound_ThrowsNotFoundException() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                vehicleService.update("nonexistent", vehicleRequestDTO));
    }

    @Test
    void updateStatus_Success() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);
        when(vehicleMapper.toResponseDTO(any(VehicleEntity.class))).thenReturn(vehicleResponseDTO);

        var result = vehicleService.updateStatus("550e8400-e29b-41d4-a716-446655440000", VehicleStatus.valueOf("INACTIVE"));

        assertNotNull(result);
        verify(vehicleRepository).save(any(VehicleEntity.class));
    }

    @Test
    void delete_Success() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicleEntity);

        vehicleService.delete("550e8400-e29b-41d4-a716-446655440000");

        verify(vehicleRepository).save(any(VehicleEntity.class));
    }

    @Test
    void delete_NotFound_ThrowsNotFoundException() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                vehicleService.delete("nonexistent"));
    }

    @Test
    void findAllPaged_Success() {
        Page<VehicleEntity> page = new PageImpl<>(List.of(vehicleEntity));
        when(vehicleRepository.findAllActive(any(Pageable.class))).thenReturn(page);
        when(vehicleMapper.toResponseDTO(any(VehicleEntity.class))).thenReturn(vehicleResponseDTO);

        var result = vehicleService.findAllPaged(PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}
