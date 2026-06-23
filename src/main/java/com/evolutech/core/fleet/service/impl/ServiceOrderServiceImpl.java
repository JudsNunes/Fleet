package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.ServiceOrderMapper;
import com.evolutech.core.fleet.model.dto.request.ServiceOrderApprovalDTO;
import com.evolutech.core.fleet.model.dto.request.ServiceOrderRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ServiceOrderResponseDTO;
import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import com.evolutech.core.fleet.repository.ServiceOrderRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.ServiceOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceOrderMapper serviceOrderMapper;

    @Override
    @Transactional
    public Optional<ServiceOrderResponseDTO> findById(String id) {
        log.info("Finding service order by id: {}", id);
        return serviceOrderRepository.findById(id).map(serviceOrderMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public ServiceOrderResponseDTO save(ServiceOrderRequestDTO body) {
        log.info("Creating service order for vehicle: {}", body.getVehicleId());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        var serviceOrderEntity = serviceOrderMapper.toEntity(body);
        serviceOrderEntity.setVehicle(vehicle);
        serviceOrderEntity.setStatus(ServiceOrderStatus.PENDING);

        var savedEntity = serviceOrderRepository.save(serviceOrderEntity);
        return serviceOrderMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional
    public ServiceOrderResponseDTO update(String id, ServiceOrderRequestDTO body) {
        log.info("Updating service order: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        var existingSO = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found with id: " + id));

        if (existingSO.getStatus() != ServiceOrderStatus.PENDING) {
            throw new BusinessException("Cannot update service order in status: " + existingSO.getStatus());
        }

        existingSO.setDescription(body.getDescription());
        existingSO.setWarrantyExpiryDate(body.getWarrantyExpiryDate());

        var updatedEntity = serviceOrderRepository.save(existingSO);
        return serviceOrderMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public ServiceOrderResponseDTO approve(String id, ServiceOrderApprovalDTO body) {
        log.info("Approving service order: {}", id);

        var existingSO = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found with id: " + id));

        if (existingSO.getStatus() != ServiceOrderStatus.PENDING) {
            throw new BusinessException("Cannot approve service order in status: " + existingSO.getStatus());
        }

        existingSO.setStatus(ServiceOrderStatus.APPROVED);
        existingSO.setApprovedBy(body.getApprovedBy());
        existingSO.setApprovedAt(LocalDateTime.now());

        var updatedEntity = serviceOrderRepository.save(existingSO);
        return serviceOrderMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public ServiceOrderResponseDTO reject(String id, ServiceOrderApprovalDTO body) {
        log.info("Rejecting service order: {}", id);

        var existingSO = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found with id: " + id));

        if (existingSO.getStatus() != ServiceOrderStatus.PENDING) {
            throw new BusinessException("Cannot reject service order in status: " + existingSO.getStatus());
        }

        existingSO.setStatus(ServiceOrderStatus.REJECTED);
        existingSO.setApprovedBy(body.getApprovedBy());
        existingSO.setApprovedAt(LocalDateTime.now());

        var updatedEntity = serviceOrderRepository.save(existingSO);
        return serviceOrderMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public ServiceOrderResponseDTO complete(String id) {
        log.info("Completing service order: {}", id);

        var existingSO = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found with id: " + id));

        if (existingSO.getStatus() != ServiceOrderStatus.APPROVED) {
            throw new BusinessException("Cannot complete service order in status: " + existingSO.getStatus());
        }

        existingSO.setStatus(ServiceOrderStatus.COMPLETED);

        var updatedEntity = serviceOrderRepository.save(existingSO);
        return serviceOrderMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Soft-deleting service order: {}", id);

        var serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service order not found with id: " + id));

        serviceOrder.setDeletedAt(LocalDateTime.now());
        serviceOrderRepository.save(serviceOrder);
    }

    @Override
    @Transactional
    public Page<ServiceOrderResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all service orders paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return serviceOrderRepository.findAllActive(pageable).map(serviceOrderMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<ServiceOrderResponseDTO> findByVehicleId(String vehicleId, Pageable pageable) {
        log.info("Finding service orders by vehicle: {}", vehicleId);
        return serviceOrderRepository.findByVehicleIdAndNotDeleted(vehicleId, pageable).map(serviceOrderMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<ServiceOrderResponseDTO> findByStatus(ServiceOrderStatus status, Pageable pageable) {
        log.info("Finding service orders by status: {}", status);
        return serviceOrderRepository.findByStatusAndNotDeleted(status, pageable).map(serviceOrderMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public List<ServiceOrderResponseDTO> findActiveWarrantiesByVehicle(String vehicleId) {
        log.info("Finding active warranties for vehicle: {}", vehicleId);
        return serviceOrderMapper.toResponseDTOList(
            serviceOrderRepository.findActiveWarrantiesByVehicle(vehicleId, LocalDateTime.now())
        );
    }
}
