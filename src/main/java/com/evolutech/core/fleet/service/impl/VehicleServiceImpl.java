package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.VehicleMapper;
import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.VehicleService;
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
    public Optional<VehicleResponseDTO> findById(String id) {
        log.info("Finding vehicle by id: {}", id);
        return vehicleRepository.findById(id).map(vehicleMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public VehicleResponseDTO save(VehicleRequestDTO body) {
        log.info("Saving vehicle with plate: {}", body.getPlate());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        vehicleRepository.findByPlateAndNotDeleted(body.getPlate())
                .ifPresent(existing -> {throw new ConflictException("Vehicle with plate " + body.getPlate() + " already exists");});

        var vehicleEntity = vehicleMapper.toEntity(body);
        vehicleEntity.setStatus(VehicleStatus.ACTIVE);
        var savedVehicle = vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toResponseDTO(savedVehicle);
    }

    @Override
    @Transactional
    public VehicleResponseDTO update(String id, VehicleRequestDTO body) {
        log.info("Updating vehicle with id: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        var existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));

        existingVehicle.setPlate(body.getPlate());
        existingVehicle.setModel(body.getModel());
        existingVehicle.setBrand(body.getBrand());
        existingVehicle.setYear(body.getYear());
        existingVehicle.setColor(body.getColor());
        existingVehicle.setMileage(body.getMileage());

        var updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toResponseDTO(updatedVehicle);
    }

    @Override
    @Transactional
    public VehicleResponseDTO updateStatus(String id, VehicleStatus status) {
        log.info("Updating status for vehicle id: {} to {}", id, status);

        var existingVehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));

        existingVehicle.setStatus(status);
        var updatedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toResponseDTO(updatedVehicle);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Soft-deleting vehicle id: {}", id);

        var vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));

        vehicle.setDeletedAt(LocalDateTime.now());
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public List<VehicleResponseDTO> findAll() {
        log.info("Finding all vehicles");
        Pageable pageable = PageRequest.of(0, 100, Sort.by("plate").ascending());
        return findAllPaged(pageable).getContent();
    }

    @Override
    @Transactional
    public Page<VehicleResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all vehicles paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return vehicleRepository.findAllActive(pageable).map(vehicleMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<VehicleResponseDTO> findByFilters(String plate, String brand, VehicleStatus status, Pageable pageable) {
        log.info("Finding vehicles with filters - plate: {}, brand: {}, status: {}", plate, brand, status);
        return vehicleRepository.findByFilters(plate, brand, status, pageable).map(vehicleMapper::toResponseDTO);
    }
}
