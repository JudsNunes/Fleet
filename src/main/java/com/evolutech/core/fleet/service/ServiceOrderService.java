package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.ServiceOrderApprovalDTO;
import com.evolutech.core.fleet.model.dto.request.ServiceOrderRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ServiceOrderResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderService {

    Optional<ServiceOrderResponseDTO> findById(String id);

    ServiceOrderResponseDTO save(ServiceOrderRequestDTO body);

    ServiceOrderResponseDTO update(String id, ServiceOrderRequestDTO body);

    ServiceOrderResponseDTO approve(String id, ServiceOrderApprovalDTO body);

    ServiceOrderResponseDTO reject(String id, ServiceOrderApprovalDTO body);

    ServiceOrderResponseDTO complete(String id);

    void delete(String id);

    Page<ServiceOrderResponseDTO> findAllPaged(Pageable pageable);

    Page<ServiceOrderResponseDTO> findByVehicleId(String vehicleId, Pageable pageable);

    Page<ServiceOrderResponseDTO> findByStatus(ServiceOrderStatus status, Pageable pageable);

    List<ServiceOrderResponseDTO> findActiveWarrantiesByVehicle(String vehicleId);
}
