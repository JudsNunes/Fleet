package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.fleet.api.model.MaintenanceRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ManutentionService {

    ManutentionResponseDTO save(@Valid MaintenanceRequestDTO request);

    Optional<ManutentionResponseDTO> findById(Long id);

    List<ManutentionResponseDTO> findAll();


    Page<ManutentionResponseDTO> findAllPaged(Pageable pageable);

    List<ManutentionResponseDTO> findByVehicleId(Long vehicleId);

    ManutentionResponseDTO update(Long id, ManutentionRequestDTO request);

    void delete(Long id);

    List<ManutentionResponseDTO> findByDone(boolean done);

    List<ManutentionResponseDTO> findByVehicleIdAndDone(Long vehicleId, boolean done);
}

