package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.MaintenanceMapper;
import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus;
import com.evolutech.core.fleet.repository.MaintenanceRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.MaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, VehicleRepository vehicleRepository, MaintenanceMapper maintenanceMapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
        this.maintenanceMapper = maintenanceMapper;
    }

    @Override
    public MaintenanceResponseDTO save(MaintenanceRequestDTO request) {
        log.info("Creating maintenance record for vehicle: {}", request.getVehicleId());

        VehicleEntity vehicle = vehicleRepository
                .findById(request.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + request.getVehicleId()));

        MaintenanceEntity entity = maintenanceMapper.toEntity(request, vehicle);
        entity.setMaintenanceStatus(MaintenanceStatus.PENDING);
        MaintenanceEntity savedEntity = maintenanceRepository.save(entity);
        log.info("Maintenance record created with id: {}", savedEntity.getId());
        return maintenanceMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponseDTO findById(String id) {
        log.debug("Finding maintenance record with id: {}", id);
        MaintenanceEntity entity = maintenanceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Maintenance record not found with id: " + id));
        return maintenanceMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findAll() {
        log.debug("Finding all maintenance records");
        Pageable pageable = PageRequest.of(0, 100, Sort.by("maintenanceDate").descending());
        return findAllPaged(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaintenanceResponseDTO> findAllPaged(Pageable pageable) {
        log.debug("Finding all maintenance records paged");
        return maintenanceRepository.findAllActive(pageable)
                .map(maintenanceMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findByVehicleId(String vehicleId) {
        log.debug("Finding maintenance records for vehicle: {}", vehicleId);
        Pageable pageable = PageRequest.of(0, 100, Sort.by("maintenanceDate").descending());
        return maintenanceRepository.findByVehicleIdAndNotDeleted(vehicleId, pageable)
                .map(maintenanceMapper::toResponseDTO)
                .getContent();
    }

    @Override
    public MaintenanceResponseDTO update(String id, MaintenanceRequestDTO request) {
        log.info("Updating maintenance record: {}", id);

        MaintenanceEntity entity = maintenanceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Maintenance record not found with id: " + id));

        VehicleEntity vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + request.getVehicleId()));

        maintenanceMapper.updateEntity(request, entity, vehicle);
        MaintenanceEntity savedEntity = maintenanceRepository.save(entity);
        return maintenanceMapper.toResponseDTO(savedEntity);
    }

    @Override
    public void delete(String id) {
        log.info("Soft-deleting maintenance record: {}", id);

        MaintenanceEntity entity = maintenanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Maintenance record not found with id: " + id));

        entity.setDeletedAt(LocalDateTime.now());
        maintenanceRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findByStatus(String status) {
        log.debug("Finding maintenance records with status: {}", status);
        MaintenanceStatus maintenanceStatus = MaintenanceStatus.valueOf(status);
        Pageable pageable = PageRequest.of(0, 100, Sort.by("maintenanceDate").descending());
        return maintenanceRepository.findByStatusAndNotDeleted(maintenanceStatus, pageable)
                .map(maintenanceMapper::toResponseDTO)
                .getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> findByVehicleIdAndStatus(String vehicleId, String status) {
        log.debug("Finding maintenance records for vehicle: {} with status: {}", vehicleId, status);
        MaintenanceStatus maintenanceStatus = MaintenanceStatus.valueOf(status);
        Pageable pageable = PageRequest.of(0, 100, Sort.by("maintenanceDate").descending());
        return maintenanceRepository.findByVehicleIdAndStatusAndNotDeleted(vehicleId, maintenanceStatus, pageable)
                .map(maintenanceMapper::toResponseDTO)
                .getContent();
    }
}
