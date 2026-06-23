package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    VehicleResponseDTO save(VehicleRequestDTO request);

    Optional<VehicleResponseDTO> findById(String id);

    List<VehicleResponseDTO> findAll();

    Page<VehicleResponseDTO> findAllPaged(Pageable pageable);

    Page<VehicleResponseDTO> findByFilters(String plate, String brand, VehicleStatus status, Pageable pageable);

    VehicleResponseDTO update(String id, VehicleRequestDTO body);

    VehicleResponseDTO updateStatus(String id, VehicleStatus status);

    void delete(String id);
}
