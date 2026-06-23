package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.MaintenanceMapper;
import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceType;
import com.evolutech.core.fleet.repository.MaintenanceRepository;
import com.evolutech.core.fleet.repository.ServiceOrderRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceImplTest {

    @Mock
    private MaintenanceRepository maintenanceRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @Mock
    private MaintenanceMapper maintenanceMapper;

    @InjectMocks
    private MaintenanceServiceImpl maintenanceService;

    private MaintenanceEntity maintenanceEntity;
    private VehicleEntity vehicleEntity;
    private MaintenanceResponseDTO maintenanceResponseDTO;
    private MaintenanceRequestDTO maintenanceRequestDTO;

    @BeforeEach
    void setUp() {
        vehicleEntity = new VehicleEntity();
        vehicleEntity.setId("550e8400-e29b-41d4-a716-446655440000");
        vehicleEntity.setPlate("ABC1D23");

        maintenanceEntity = new MaintenanceEntity();
        maintenanceEntity.setId("550e8400-e29b-41d4-a716-446655440001");
        maintenanceEntity.setMaintenanceDate(LocalDate.now());
        maintenanceEntity.setDescription("Troca de óleo");
        maintenanceEntity.setType(MaintenanceType.MAINTENANCE);
        maintenanceEntity.setCost(150.0);
        maintenanceEntity.setMileage(50000.0);
        maintenanceEntity.setNextMileage(60000.0);
        maintenanceEntity.setMaintenanceStatus(MaintenanceStatus.PENDING);
        maintenanceEntity.setVehicle(vehicleEntity);
        maintenanceEntity.setCreatedAt(LocalDateTime.now());
        maintenanceEntity.setUpdatedAt(LocalDateTime.now());

        maintenanceResponseDTO = MaintenanceResponseDTO.builder()
                .id("550e8400-e29b-41d4-a716-446655440001")
                .maintenanceDate(LocalDate.now())
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0)
                .mileage(50000.0)
                .nextMileage(60000.0)
                .status("PENDING")
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .build();

        maintenanceRequestDTO = MaintenanceRequestDTO.builder()
                .maintenanceDate(LocalDate.now())
                .description("Troca de óleo")
                .type("MAINTENANCE")
                .cost(150.0)
                .mileage(50000.0)
                .nextMileage(60000.0)
                .vehicleId("550e8400-e29b-41d4-a716-446655440000")
                .serviceOrderId("550e8400-e29b-41d4-a716-446655440002")
                .build();
    }

    @Test
    void save_Success() {
        ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity();
        serviceOrderEntity.setId("550e8400-e29b-41d4-a716-446655440002");
        serviceOrderEntity.setStatus(com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus.APPROVED);

        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(serviceOrderRepository.findById(anyString())).thenReturn(Optional.of(serviceOrderEntity));
        when(maintenanceMapper.toEntity(any(MaintenanceRequestDTO.class), any(VehicleEntity.class), any(ServiceOrderEntity.class)))
                .thenReturn(maintenanceEntity);
        when(maintenanceRepository.save(any(MaintenanceEntity.class))).thenReturn(maintenanceEntity);
        when(maintenanceMapper.toResponseDTO(any(MaintenanceEntity.class))).thenReturn(maintenanceResponseDTO);

        var result = maintenanceService.save(maintenanceRequestDTO);

        assertNotNull(result);
        assertEquals("Troca de óleo", result.getDescription());
        verify(maintenanceRepository).save(any(MaintenanceEntity.class));
    }

    @Test
    void save_VehicleNotFound_ThrowsNotFoundException() {
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> maintenanceService.save(maintenanceRequestDTO));
    }

    @Test
    void findById_Found() {
        when(maintenanceRepository.findById(anyString())).thenReturn(Optional.of(maintenanceEntity));
        when(maintenanceMapper.toResponseDTO(any(MaintenanceEntity.class))).thenReturn(maintenanceResponseDTO);

        var result = maintenanceService.findById("550e8400-e29b-41d4-a716-446655440001");

        assertNotNull(result);
        assertEquals("Troca de óleo", result.getDescription());
    }

    @Test
    void findById_NotFound_ThrowsNotFoundException() {
        when(maintenanceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> maintenanceService.findById("nonexistent"));
    }

    @Test
    void update_Success() {
        ServiceOrderEntity serviceOrderEntity = new ServiceOrderEntity();
        serviceOrderEntity.setId("550e8400-e29b-41d4-a716-446655440002");

        when(maintenanceRepository.findById(anyString())).thenReturn(Optional.of(maintenanceEntity));
        when(vehicleRepository.findById(anyString())).thenReturn(Optional.of(vehicleEntity));
        when(serviceOrderRepository.findById(anyString())).thenReturn(Optional.of(serviceOrderEntity));
        when(maintenanceRepository.save(any(MaintenanceEntity.class))).thenReturn(maintenanceEntity);
        when(maintenanceMapper.toResponseDTO(any(MaintenanceEntity.class))).thenReturn(maintenanceResponseDTO);

        var result = maintenanceService.update("550e8400-e29b-41d4-a716-446655440001", maintenanceRequestDTO);

        assertNotNull(result);
        verify(maintenanceRepository).save(any(MaintenanceEntity.class));
    }

    @Test
    void delete_Success() {
        when(maintenanceRepository.findById(anyString())).thenReturn(Optional.of(maintenanceEntity));
        when(maintenanceRepository.save(any(MaintenanceEntity.class))).thenReturn(maintenanceEntity);

        maintenanceService.delete("550e8400-e29b-41d4-a716-446655440001");

        verify(maintenanceRepository).save(any(MaintenanceEntity.class));
    }

    @Test
    void delete_NotFound_ThrowsNotFoundException() {
        when(maintenanceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> maintenanceService.delete("nonexistent"));
    }

    @Test
    void findByVehicleId_Success() {
        Page<MaintenanceEntity> page = new PageImpl<>(List.of(maintenanceEntity));
        when(maintenanceRepository.findByVehicleIdAndNotDeleted(anyString(), any(Pageable.class)))
                .thenReturn(page);
        when(maintenanceMapper.toResponseDTO(any(MaintenanceEntity.class))).thenReturn(maintenanceResponseDTO);

        var result = maintenanceService.findByVehicleId("550e8400-e29b-41d4-a716-446655440000");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findByStatus_Success() {
        Page<MaintenanceEntity> page = new PageImpl<>(List.of(maintenanceEntity));
        when(maintenanceRepository.findByStatusAndNotDeleted(any(MaintenanceStatus.class), any(Pageable.class)))
                .thenReturn(page);
        when(maintenanceMapper.toResponseDTO(any(MaintenanceEntity.class))).thenReturn(maintenanceResponseDTO);

        var result = maintenanceService.findByStatus("PENDING");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
