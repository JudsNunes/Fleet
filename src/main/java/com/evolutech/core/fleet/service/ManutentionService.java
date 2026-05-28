package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ManutentionService {

    ManutentionResponseDTO save(ManutentionRequestDTO request);

    Optional<ManutentionResponseDTO> findById(String id);

    List<ManutentionResponseDTO> findAll();

    List<ManutentionResponseDTO> findByVehicleId(Long vehicleId);

    ManutentionResponseDTO update(String id, ManutentionRequestDTO request);

    void delete(String id);

    List<ManutentionResponseDTO> findByDone(boolean done);

    List<ManutentionResponseDTO> findByVehicleIdAndDone(Long vehicleId, boolean done);
}

