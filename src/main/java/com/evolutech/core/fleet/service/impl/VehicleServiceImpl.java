package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.api.model.VehicleRequestDTO;
import com.evolutech.core.fleet.api.model.VehicleResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.mapper.VehicleMapper;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Optional<VehicleResponseDTO> findById(Long id) {
        log.info("Starting findVehicleById with id: {}", id);
        return Optional.of(vehicleRepository.findById(id)
                .map(vehicleMapper::toResponseDTO)
                .orElseThrow(() -> new BusinessException("Could not find vehicle with id: " + id)));
    }

    @Override
    @Transactional
    public VehicleResponseDTO save(VehicleRequestDTO body) {
        log.info("Starting saveByEntity with body: {}", body);
        if (body == null) {
            log.error("body is null");
            throw new BusinessException("body cannot be null");
        }
        var vehicleEntity = vehicleMapper.toEntity(body);
        var savedVehicle = vehicleRepository.save(vehicleEntity);
        log.debug("Finished Mappng vehicle entity saved and createdAt: {}", LocalDateTime.now());
        log.debug("Vehicle saved: {}", savedVehicle);
        return vehicleMapper.toResponseDTO(savedVehicle);
    }

    @Override
    @Transactional
    public VehicleResponseDTO update(VehicleRequestDTO body) {
        log.info("Starting updateByEntity with body: {}", body);
        if (body == null) {
            log.error("body is null");
            throw new BusinessException("body cannot be null");
        }
        var vehicleEntity = vehicleMapper.toEntity(body);
        var updatedVehicle = vehicleRepository.save(vehicleEntity);
        log.debug("Finished Mappng vehicle entity updated and updatedAt: {}", LocalDateTime.now());
        log.debug("Vehicle updated: {}", updatedVehicle);
        return vehicleMapper.toResponseDTO(updatedVehicle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Starting delete process for vehicle id: {}", id);

        if (id == null) {
            log.error("Delete failed: ID is null");
            throw new BusinessException("ID cannot be null");
        }

        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> {
            log.warn("Delete failed: Vehicle id: {} not found", id);
            return new BusinessException("Vehicle not found with id: " + id);
        });

        vehicleRepository.delete(vehicle);
        log.debug("Vehicle id:{} successfully deleted", id);
    }

    @Override
    @Transactional
    public Optional<VehicleResponseDTO> findByPlate(VehicleRequestDTO body) {
        log.info("Starting findByPlate with plate: {}", (body != null) ? body.getPlate() : "null");

        if (body == null || body.getPlate() == null) {
            log.warn("Search attempt with null body or plate");
            return Optional.empty();
        }

        return vehicleRepository.findByPlate(body.getPlate())
                .map(vehicleMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public List<VehicleResponseDTO> findAll() {
        log.info("Starting findAll vehicles");
        Sort sort = Sort.by("id").ascending().and(Sort.by("Name").ascending());
        var vehicles = vehicleRepository.findAll(sort);
        log.debug("Found {} vehicles", vehicles.size());
        return vehicleMapper.toResponseDTOList(vehicles);
    }

}

