package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.VehicleAssignmentMapper;
import com.evolutech.core.fleet.model.dto.request.VehicleAssignmentRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleAssignmentResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.entity.VehicleAssignmentEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.AssignmentStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import com.evolutech.core.fleet.repository.DriverRepository;
import com.evolutech.core.fleet.repository.VehicleAssignmentRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.VehicleAssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleAssignmentServiceImpl implements VehicleAssignmentService {

    private final VehicleAssignmentRepository vehicleAssignmentRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final VehicleAssignmentMapper vehicleAssignmentMapper;

    @Override
    @Transactional
    public Optional<VehicleAssignmentResponseDTO> findById(String id) {
        log.info("Finding assignment by id: {}", id);
        return vehicleAssignmentRepository.findById(id).map(vehicleAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public VehicleAssignmentResponseDTO save(VehicleAssignmentRequestDTO body) {
        log.info("Creating assignment for vehicle: {} and driver: {}", body.getVehicleId(), body.getDriverId());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        DriverEntity driver = driverRepository.findById(body.getDriverId())
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + body.getDriverId()));

        if (vehicle.getStatus() != VehicleStatus.ACTIVE) {
            throw new BusinessException("Vehicle must be ACTIVE for assignment");
        }

        if (driver.getStatus() != DriverStatus.ACTIVE) {
            throw new BusinessException("Driver must be ACTIVE for assignment");
        }

        LocalDate endDate = body.getEndDate() != null ? body.getEndDate() : LocalDate.now().plusYears(1);

        var overlappingVehicle = vehicleAssignmentRepository.findOverlappingByVehicle(body.getVehicleId(), body.getStartDate(), endDate);
        if (!overlappingVehicle.isEmpty()) {
            throw new ConflictException("Vehicle already has an active assignment in this period");
        }

        var overlappingDriver = vehicleAssignmentRepository.findOverlappingByDriver(body.getDriverId(), body.getStartDate(), endDate);
        if (!overlappingDriver.isEmpty()) {
            throw new ConflictException("Driver already has an active assignment in this period");
        }

        VehicleAssignmentEntity entity = vehicleAssignmentMapper.toEntity(body, vehicle, driver);
        entity.setStatus(AssignmentStatus.ACTIVE);

        var savedEntity = vehicleAssignmentRepository.save(entity);
        return vehicleAssignmentMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional
    public VehicleAssignmentResponseDTO update(String id, VehicleAssignmentRequestDTO body) {
        log.info("Updating assignment: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        var existingAssignment = vehicleAssignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));

        if (existingAssignment.getStatus() != AssignmentStatus.ACTIVE) {
            throw new BusinessException("Can only update ACTIVE assignments");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        DriverEntity driver = driverRepository.findById(body.getDriverId())
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + body.getDriverId()));

        existingAssignment.setVehicle(vehicle);
        existingAssignment.setDriver(driver);
        existingAssignment.setStartDate(body.getStartDate());
        existingAssignment.setEndDate(body.getEndDate());
        existingAssignment.setAssignedBy(body.getAssignedBy());
        existingAssignment.setNotes(body.getNotes());

        var updatedEntity = vehicleAssignmentRepository.save(existingAssignment);
        return vehicleAssignmentMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public VehicleAssignmentResponseDTO end(String id) {
        log.info("Ending assignment: {}", id);

        var existingAssignment = vehicleAssignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));

        if (existingAssignment.getStatus() != AssignmentStatus.ACTIVE) {
            throw new BusinessException("Can only end ACTIVE assignments");
        }

        existingAssignment.setStatus(AssignmentStatus.ENDED);
        existingAssignment.setEndDate(LocalDate.now());

        var updatedEntity = vehicleAssignmentRepository.save(existingAssignment);
        return vehicleAssignmentMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Soft-deleting assignment: {}", id);

        var assignment = vehicleAssignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found with id: " + id));

        assignment.setDeletedAt(LocalDateTime.now());
        vehicleAssignmentRepository.save(assignment);
    }

    @Override
    @Transactional
    public Page<VehicleAssignmentResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all assignments paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return vehicleAssignmentRepository.findAllActive(pageable).map(vehicleAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<VehicleAssignmentResponseDTO> findByVehicleId(String vehicleId, Pageable pageable) {
        log.info("Finding assignments by vehicle: {}", vehicleId);
        return vehicleAssignmentRepository.findByVehicleIdAndNotDeleted(vehicleId, pageable).map(vehicleAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<VehicleAssignmentResponseDTO> findByDriverId(String driverId, Pageable pageable) {
        log.info("Finding assignments by driver: {}", driverId);
        return vehicleAssignmentRepository.findByDriverIdAndNotDeleted(driverId, pageable).map(vehicleAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<VehicleAssignmentResponseDTO> findByStatus(AssignmentStatus status, Pageable pageable) {
        log.info("Finding assignments by status: {}", status);
        return vehicleAssignmentRepository.findByStatusAndNotDeleted(status, pageable).map(vehicleAssignmentMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public List<VehicleAssignmentResponseDTO> findActiveAssignments() {
        log.info("Finding active assignments");
        return vehicleAssignmentMapper.toResponseDTOList(vehicleAssignmentRepository.findActiveAssignments());
    }
}
