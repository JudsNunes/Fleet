package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaintenanceService {

    MaintenanceResponseDTO save(MaintenanceRequestDTO request);

    MaintenanceResponseDTO findById(String id);

    List<MaintenanceResponseDTO> findAll();

    Page<MaintenanceResponseDTO> findAllPaged(Pageable pageable);

    List<MaintenanceResponseDTO> findByVehicleId(String vehicleId);

    MaintenanceResponseDTO update(String id, MaintenanceRequestDTO request);

    void delete(String id);

    List<MaintenanceResponseDTO> findByStatus(String status);

    List<MaintenanceResponseDTO> findByVehicleIdAndStatus(String vehicleId, String status);
}
