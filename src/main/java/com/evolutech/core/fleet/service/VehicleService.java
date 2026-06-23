package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.fleet.api.model.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    VehicleDTO save(VehicleRequestDTO request);

    Optional<VehicleDTO> findById(Long id);

    List<VehicleDTO> findAll();

    /**
     * Find all vehicles with pagination support
     * @param pageable Pagination parameters (page, size, sort)
     * @return Page of VehicleDTO with pagination metadata
     */
    Page<VehicleDTO> findAllPaged(Pageable pageable);

    VehicleDTO update(VehicleRequestDTO request);

    void delete(Long id);

    Optional<VehicleDTO> findByPlate(VehicleRequestDTO request);
}

