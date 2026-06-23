package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.FineRequestDTO;
import com.evolutech.core.fleet.model.dto.response.FineResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.FineStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FineService {

    Optional<FineResponseDTO> findById(String id);

    FineResponseDTO save(FineRequestDTO body);

    FineResponseDTO update(String id, FineRequestDTO body);

    void delete(String id);

    Page<FineResponseDTO> findAllPaged(Pageable pageable);

    Page<FineResponseDTO> findByDriverCpf(String driverCpf, Pageable pageable);

    Page<FineResponseDTO> findByVehicleId(String vehicleId, Pageable pageable);

    Page<FineResponseDTO> findByStatus(FineStatus status, Pageable pageable);
}
