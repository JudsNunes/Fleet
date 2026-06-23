package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.mapper.ManutentionMapper;
import com.evolutech.core.fleet.model.entity.ManutentionEntity;
import com.evolutech.core.fleet.model.utils.enums.ManutentionDoneStatus;
import com.evolutech.core.fleet.repository.ManutentionRepository;
import com.evolutech.core.fleet.service.ManutentionService;
import com.evolutech.fleet.api.model.MaintenanceRequestDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ManutentionServiceImpl implements ManutentionService {

    private final ManutentionRepository manutentionRepository;
    private final ManutentionMapper manutentionMapper;

    public ManutentionServiceImpl(ManutentionRepository manutentionRepository, ManutentionMapper manutentionMapper) {
        this.manutentionRepository = manutentionRepository;
        this.manutentionMapper = manutentionMapper;
    }

    @Override
    public ManutentionResponseDTO save(@Valid MaintenanceRequestDTO request) {
        log.info("Creating new maintenance record with description: {}", request.getDescription());

        try {
            ManutentionEntity entity = manutentionMapper.toEntity(request);
            ManutentionEntity savedEntity = manutentionRepository.save(entity);
            log.info("Maintenance record created successfully with ID: {}", savedEntity.getId());
            return manutentionMapper.toResponseDTO(savedEntity);
        } catch (IllegalArgumentException e) {
            log.error("Error creating maintenance record: {}", e.getMessage());
            throw new BusinessException("Failed to create maintenance record: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManutentionResponseDTO> findById(Long id) {
        log.debug("Finding maintenance record with ID: {}", id);
        return manutentionRepository.findById(id)
                .map(manutentionMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findAll() {
        log.debug("Finding all maintenance records with default pagination");
        Pageable pageable = PageRequest.of(0, 100, Sort.by("manutentionDate").descending());
        return findAllPaged(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManutentionResponseDTO> findAllPaged(Pageable pageable) {
        log.debug("Finding all maintenance records paged");
        return manutentionRepository.findAllActive(pageable)
                .map(manutentionMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByVehicleId(Long vehicleId) {
        log.debug("Finding maintenance records for vehicle ID: {}", vehicleId);
        Pageable pageable = PageRequest.of(0, 100, Sort.by("manutentionDate").descending());
        return findByVehicleIdPaged(vehicleId, pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Page<ManutentionResponseDTO> findByVehicleIdPaged(Long vehicleId, Pageable pageable) {
        log.debug("Finding maintenance records paged for vehicle ID: {}", vehicleId);
        return manutentionRepository.findByVehicleIdAndNotDeleted(vehicleId, pageable)
                .map(manutentionMapper::toResponseDTO);
    }

    @Override
    public ManutentionResponseDTO update(Long id, ManutentionRequestDTO request) {
        log.info("Updating maintenance record with ID: {}", id);

        ManutentionEntity entity = manutentionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Maintenance record not found with ID: {}", id);
                    return new BusinessException("Maintenance record not found with ID: " + id);
                });

        try {
            ManutentionEntity updatedEntity = manutentionMapper.updateEntity(request, entity);
            ManutentionEntity savedEntity = manutentionRepository.save(updatedEntity);
            log.info("Maintenance record updated successfully with ID: {}", savedEntity.getId());
            return manutentionMapper.toResponseDTO(savedEntity);
        } catch (IllegalArgumentException e) {
            log.error("Error updating maintenance record: {}", e.getMessage());
            throw new BusinessException("Failed to update maintenance record: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Soft-deleting maintenance record with ID: {}", id);

        ManutentionEntity entity = manutentionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Maintenance record not found with ID: {}", id);
                    return new BusinessException("Maintenance record not found with ID: " + id);
                });

        entity.setDeletedAt(LocalDateTime.now());
        manutentionRepository.save(entity);
        log.info("Maintenance record soft-deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByDone(boolean done) {
        log.debug("Finding maintenance records with done status: {}", done);
        ManutentionDoneStatus status = done ? ManutentionDoneStatus.COMPLETED : ManutentionDoneStatus.PENDING;
        Pageable pageable = PageRequest.of(0, 100, Sort.by("manutentionDate").descending());
        return findByDonePaged(status, pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Page<ManutentionResponseDTO> findByDonePaged(ManutentionDoneStatus done, Pageable pageable) {
        log.debug("Finding maintenance records paged with done status: {}", done);
        return manutentionRepository.findByDoneAndNotDeleted(done, pageable)
                .map(manutentionMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByVehicleIdAndDone(Long vehicleId, boolean done) {
        log.debug("Finding maintenance records for vehicle ID: {} with done status: {}", vehicleId, done);
        ManutentionDoneStatus status = done ? ManutentionDoneStatus.COMPLETED : ManutentionDoneStatus.PENDING;
        Pageable pageable = PageRequest.of(0, 100, Sort.by("manutentionDate").descending());
        return findByVehicleIdAndDonePaged(vehicleId, status, pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Page<ManutentionResponseDTO> findByVehicleIdAndDonePaged(Long vehicleId, ManutentionDoneStatus done, Pageable pageable) {
        log.debug("Finding maintenance records paged for vehicle ID: {} with done status: {}", vehicleId, done);
        return manutentionRepository.findByVehicleIdAndDoneAndNotDeleted(vehicleId, done, pageable)
                .map(manutentionMapper::toResponseDTO);
    }
}

