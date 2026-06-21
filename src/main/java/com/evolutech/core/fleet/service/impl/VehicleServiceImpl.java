package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.mapper.VehicleMapper;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.VehicleService;
import com.evolutech.fleet.api.model.VehicleDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    @Transactional
    public Optional<VehicleDTO> findById(Long id) {
        log.info("Starting findVehicleById with id: {}", id);
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toVehicleDTO);
    }

    @Override
    @Transactional
    public VehicleDTO save(VehicleRequestDTO body) {
        log.info("Starting saveByEntity with body: {}", body);
        if (body == null) {
            log.error("body is null");
            throw new BusinessException("body cannot be null");
        }
        var vehicleEntity = vehicleMapper.toEntity(body);
        var savedVehicle = vehicleRepository.save(vehicleEntity);
        log.debug("Finished Mapping vehicle entity saved and createdAt: {}", LocalDateTime.now());
        log.debug("Vehicle saved: {}", savedVehicle);
        return vehicleMapper.toVehicleDTO(savedVehicle);
    }

    @Override
    @Transactional
    public VehicleDTO update(VehicleRequestDTO body) {
        log.info("Starting updateByEntity with body: {}", body);
        if (body == null) {
            log.error("body is null");
            throw new BusinessException("body cannot be null");
        }
        var vehicleEntity = vehicleMapper.toEntity(body);
        var updatedVehicle = vehicleRepository.save(vehicleEntity);
        log.debug("Finished Mapping vehicle entity updated and updatedAt: {}", LocalDateTime.now());
        log.debug("Vehicle updated: {}", updatedVehicle);
        return vehicleMapper.toVehicleDTO(updatedVehicle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Starting soft-delete process for vehicle id: {}", id);

        if (id == null) {
            log.error("Delete failed: ID is null");
            throw new BusinessException("ID cannot be null");
        }

        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> {
            log.warn("Delete failed: Vehicle id: {} not found", id);
            return new BusinessException("Vehicle not found with id: " + id);
        });

        vehicle.setDeletedAt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
        log.debug("Vehicle id:{} successfully soft-deleted", id);
    }

    @Override
    @Transactional
    public Optional<VehicleDTO> findByPlate(VehicleRequestDTO body) {
        log.info("Starting findByPlate with plate: {}", (body != null) ? body.getPlate() : "null");

        if (body == null || body.getPlate() == null) {
            log.warn("Search attempt with null body or plate");
            return Optional.empty();
        }

        return vehicleRepository.findByPlateAndNotDeleted(body.getPlate())
                .map(vehicleMapper::toVehicleDTO);
    }

    @Override
    @Transactional
    public List<VehicleDTO> findAll() {
        log.info("Starting findAll vehicles with default pagination");
        Pageable pageable = PageRequest.of(0, 100, Sort.by("id").ascending());
        return findAllPaged(pageable).getContent();
    }

    @Override
    @Transactional
    public Page<VehicleDTO> findAllPaged(Pageable pageable) {
        log.info("Starting findAllPaged with pageable: {}", pageable);
        Page<VehicleResponseDTO> page = vehicleRepository.findAllActive(pageable)
                .map(vehicleMapper::toResponseDTO);
        log.debug("Found {} active vehicles", page.getTotalElements());
        return page.map(vehicleMapper::toVehicleDTO);
    }
}

