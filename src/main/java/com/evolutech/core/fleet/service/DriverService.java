package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.DriverRequestDTO;
import com.evolutech.core.fleet.model.dto.response.DriverResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    Optional<DriverResponseDTO> findById(String id);

    DriverResponseDTO save(DriverRequestDTO body);

    DriverResponseDTO update(String id, DriverRequestDTO body);

    DriverResponseDTO updateStatus(String id, DriverStatus status);

    void delete(String id);

    Page<DriverResponseDTO> findAllPaged(Pageable pageable);

    Page<DriverResponseDTO> findByFilters(String name, String cpf, DriverStatus status, DriverLicenseStatus cnhStatus, Pageable pageable);

    Optional<DriverResponseDTO> findByCpf(String cpf);

    List<DriverResponseDTO> findExpiringCnhs(int daysAhead);
}
