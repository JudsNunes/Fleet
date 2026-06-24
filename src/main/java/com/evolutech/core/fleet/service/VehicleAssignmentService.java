package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.VehicleAssignmentRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleAssignmentResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.AssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

public interface VehicleAssignmentService {

    Optional<VehicleAssignmentResponseDTO> findById(String id);

    VehicleAssignmentResponseDTO save(VehicleAssignmentRequestDTO body);

    VehicleAssignmentResponseDTO update(String id, VehicleAssignmentRequestDTO body);

    VehicleAssignmentResponseDTO end(String id);

    void delete(String id);

    Page<VehicleAssignmentResponseDTO> findAllPaged(Pageable pageable);

    Page<VehicleAssignmentResponseDTO> findByVehicleId(String vehicleId, Pageable pageable);

    Page<VehicleAssignmentResponseDTO> findByDriverId(String driverId, Pageable pageable);

    Page<VehicleAssignmentResponseDTO> findByStatus(AssignmentStatus status, Pageable pageable);

    List<VehicleAssignmentResponseDTO> findActiveAssignments();
}
