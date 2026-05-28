package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.api.model.ManutentionRequestDTO;
import com.evolutech.core.fleet.api.model.ManutentionResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.mapper.ManutentionMapper;
import com.evolutech.core.fleet.model.entity.Manutention;
import com.evolutech.core.fleet.repository.ManutentionRepository;
import com.evolutech.core.fleet.service.ManutentionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ManutentionResponseDTO save(ManutentionRequestDTO request) {
        log.info("Creating new maintenance record with description: {}", request.getDescription());

        try {
            Manutention entity = manutentionMapper.toEntity(request);
            Manutention savedEntity = manutentionRepository.save(entity);
            log.info("Maintenance record created successfully with ID: {}", savedEntity.getId());
            return manutentionMapper.toDto(savedEntity);
        } catch (IllegalArgumentException e) {
            log.error("Error creating maintenance record: {}", e.getMessage());
            throw new BusinessException("Failed to create maintenance record: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManutentionResponseDTO> findById(String id) {
        log.debug("Finding maintenance record with ID: {}", id);
        return manutentionRepository.findById(id)
                .map(manutentionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findAll() {
        log.debug("Finding all maintenance records");
        return manutentionRepository.findAll()
                .stream()
                .map(manutentionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByVehicleId(Long vehicleId) {
        log.debug("Finding maintenance records for vehicle ID: {}", vehicleId);
        return manutentionRepository.findAll()
                .stream()
                .filter(m -> m.getVehicle().getId().equals(vehicleId))
                .map(manutentionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ManutentionResponseDTO update(String id, ManutentionRequestDTO request) {
        log.info("Updating maintenance record with ID: {}", id);

        Manutention entity = manutentionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Maintenance record not found with ID: {}", id);
                    return new BusinessException("Maintenance record not found with ID: " + id);
                });

        try {
            Manutention updatedEntity = manutentionMapper.updateEntity(request, entity);
            Manutention savedEntity = manutentionRepository.save(updatedEntity);
            log.info("Maintenance record updated successfully with ID: {}", savedEntity.getId());
            return manutentionMapper.toDto(savedEntity);
        } catch (IllegalArgumentException e) {
            log.error("Error updating maintenance record: {}", e.getMessage());
            throw new BusinessException("Failed to update maintenance record: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        log.info("Deleting maintenance record with ID: {}", id);

        Manutention entity = manutentionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Maintenance record not found with ID: {}", id);
                    return new BusinessException("Maintenance record not found with ID: " + id);
                });

        manutentionRepository.delete(entity);
        log.info("Maintenance record deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByDone(boolean done) {
        log.debug("Finding maintenance records with done status: {}", done);
        return manutentionRepository.findAll()
                .stream()
                .filter(m -> m.isDone() == done)
                .map(manutentionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutentionResponseDTO> findByVehicleIdAndDone(Long vehicleId, boolean done) {
        log.debug("Finding maintenance records for vehicle ID: {} with done status: {}", vehicleId, done);
        return manutentionRepository.findAll()
                .stream()
                .filter(m -> m.getVehicle().getId().equals(vehicleId) && m.isDone() == done)
                .map(manutentionMapper::toDto)
                .collect(Collectors.toList());
    }
}

