package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.TripMapper;
import com.evolutech.core.fleet.model.dto.request.TripRequestDTO;
import com.evolutech.core.fleet.model.dto.response.TripResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.entity.TripEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import com.evolutech.core.fleet.model.utils.enums.TripStatus;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import com.evolutech.core.fleet.repository.DriverRepository;
import com.evolutech.core.fleet.repository.TripRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.TripService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final TripMapper tripMapper;

    @Override
    @Transactional
    public Optional<TripResponseDTO> findById(String id) {
        log.info("Finding trip by id: {}", id);
        return tripRepository.findById(id).map(tripMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public TripResponseDTO save(TripRequestDTO body) {
        log.info("Creating trip for vehicle: {} and driver: {}", body.getVehicleId(), body.getDriverId());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        DriverEntity driver = driverRepository.findById(body.getDriverId())
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + body.getDriverId()));

        if (vehicle.getStatus() != VehicleStatus.ACTIVE) {
            throw new BusinessException("Vehicle must be ACTIVE to create a trip");
        }

        if (driver.getStatus() != DriverStatus.ACTIVE) {
            throw new BusinessException("Driver must be ACTIVE to create a trip");
        }

        if (driver.getCnhStatus() != DriverLicenseStatus.ACTIVE) {
            throw new BusinessException("Driver CNH must be ACTIVE to create a trip");
        }

        TripEntity tripEntity = tripMapper.toEntity(body, vehicle, driver);
        tripEntity.setStatus(TripStatus.PLANNED);

        var savedTrip = tripRepository.save(tripEntity);
        return tripMapper.toResponseDTO(savedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO update(String id, TripRequestDTO body) {
        log.info("Updating trip: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        var existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        if (existingTrip.getStatus() != TripStatus.PLANNED) {
            throw new BusinessException("Can only update trips in PLANNED status");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        DriverEntity driver = driverRepository.findById(body.getDriverId())
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + body.getDriverId()));

        existingTrip.setVehicle(vehicle);
        existingTrip.setDriver(driver);
        existingTrip.setDescription(body.getDescription());
        existingTrip.setOrigin(body.getOrigin());
        existingTrip.setDestination(body.getDestination());
        existingTrip.setPlannedDistanceKm(body.getPlannedDistanceKm());
        existingTrip.setDepartureDate(body.getDepartureDate());
        existingTrip.setStartMileage(body.getStartMileage());

        var updatedTrip = tripRepository.save(existingTrip);
        return tripMapper.toResponseDTO(updatedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO start(String id, Double startMileage) {
        log.info("Starting trip: {}", id);

        var existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        if (existingTrip.getStatus() != TripStatus.PLANNED) {
            throw new BusinessException("Can only start trips in PLANNED status");
        }

        if (startMileage == null) {
            throw new BusinessException("Start mileage is required");
        }

        var activeTrip = tripRepository.findActiveTripByVehicle(existingTrip.getVehicle().getId());
        if (activeTrip.isPresent() && !activeTrip.get().getId().equals(id)) {
            throw new BusinessException("Vehicle already has an active trip");
        }

        existingTrip.setStatus(TripStatus.IN_PROGRESS);
        existingTrip.setStartMileage(startMileage);

        var updatedTrip = tripRepository.save(existingTrip);
        return tripMapper.toResponseDTO(updatedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO complete(String id, Double endMileage) {
        log.info("Completing trip: {}", id);

        var existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        if (existingTrip.getStatus() != TripStatus.IN_PROGRESS) {
            throw new BusinessException("Can only complete trips in IN_PROGRESS status");
        }

        if (endMileage == null) {
            throw new BusinessException("End mileage is required");
        }

        if (endMileage <= existingTrip.getStartMileage()) {
            throw new BusinessException("End mileage must be greater than start mileage");
        }

        existingTrip.setStatus(TripStatus.COMPLETED);
        existingTrip.setEndMileage(endMileage);
        existingTrip.setArrivalDate(LocalDateTime.now());
        existingTrip.setActualDistanceKm(endMileage - existingTrip.getStartMileage());

        if (existingTrip.getPlannedDistanceKm() != null && existingTrip.getPlannedDistanceKm() > 0) {
            double deviationPercent = ((existingTrip.getActualDistanceKm() - existingTrip.getPlannedDistanceKm()) / existingTrip.getPlannedDistanceKm()) * 100;
            if (deviationPercent > 15) {
                existingTrip.setRouteDeviation(true);
            }
        }

        VehicleEntity vehicle = existingTrip.getVehicle();
        vehicle.setMileage(endMileage);
        vehicleRepository.save(vehicle);

        var updatedTrip = tripRepository.save(existingTrip);
        return tripMapper.toResponseDTO(updatedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO cancel(String id) {
        log.info("Cancelling trip: {}", id);

        var existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        if (existingTrip.getStatus() == TripStatus.COMPLETED) {
            throw new BusinessException("Cannot cancel a completed trip");
        }

        existingTrip.setStatus(TripStatus.CANCELLED);

        var updatedTrip = tripRepository.save(existingTrip);
        return tripMapper.toResponseDTO(updatedTrip);
    }

    @Override
    @Transactional
    public TripResponseDTO registerDeviation(String id, String justification) {
        log.info("Registering deviation justification for trip: {}", id);

        var existingTrip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        if (!existingTrip.getRouteDeviation()) {
            throw new BusinessException("Trip does not have a route deviation");
        }

        if (justification == null || justification.isBlank()) {
            throw new BusinessException("Justification cannot be empty");
        }

        existingTrip.setDeviationJustification(justification);

        var updatedTrip = tripRepository.save(existingTrip);
        return tripMapper.toResponseDTO(updatedTrip);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Soft-deleting trip: {}", id);

        var trip = tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with id: " + id));

        trip.setDeletedAt(LocalDateTime.now());
        tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Page<TripResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all trips paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return tripRepository.findAllActive(pageable).map(tripMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<TripResponseDTO> findByFilters(String vehicleId, String driverId, TripStatus status, Pageable pageable) {
        log.info("Finding trips with filters - vehicle: {}, driver: {}, status: {}", vehicleId, driverId, status);
        return tripRepository.findByFilters(vehicleId, driverId, status, pageable).map(tripMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<TripResponseDTO> findByVehicleId(String vehicleId, Pageable pageable) {
        log.info("Finding trips by vehicle: {}", vehicleId);
        return tripRepository.findByVehicleIdAndNotDeleted(vehicleId, pageable).map(tripMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<TripResponseDTO> findByDriverId(String driverId, Pageable pageable) {
        log.info("Finding trips by driver: {}", driverId);
        return tripRepository.findByDriverIdAndNotDeleted(driverId, pageable).map(tripMapper::toResponseDTO);
    }
}
