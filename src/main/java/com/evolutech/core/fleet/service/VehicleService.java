package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.api.model.VehicleRequestDTO;
import com.evolutech.core.fleet.api.model.VehicleResponseDTO;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    VehicleResponseDTO save(VehicleRequestDTO request);

    Optional<VehicleResponseDTO> findById(Long id);

    List<VehicleResponseDTO> findAll();

    VehicleResponseDTO update(VehicleRequestDTO request);

    void delete(Long id);

    Optional<VehicleResponseDTO> findByPlate(VehicleRequestDTO request);
}

