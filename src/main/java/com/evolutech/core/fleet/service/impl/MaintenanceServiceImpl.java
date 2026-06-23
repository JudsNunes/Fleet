package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.MaintenanceMapper;
import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.FuelType;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceType;
import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import com.evolutech.core.fleet.repository.MaintenanceRepository;
import com.evolutech.core.fleet.repository.ServiceOrderRepository;
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
    private final ServiceOrderRepository serviceOrderRepository;
    private final MaintenanceMapper maintenanceMapper;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, VehicleRepository vehicleRepository, ServiceOrderRepository serviceOrderRepository, MaintenanceMapper maintenanceMapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceOrderRepository = serviceOrderRepository;
        this.maintenanceMapper = maintenanceMapper;
    }

    @Override
    public MaintenanceResponseDTO save(MaintenanceRequestDTO request) {
        log.info("Creating maintenance record for vehicle: {}", request.getVehicleId());

        VehicleEntity vehicle = vehicleRepository
                .findById(request.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + request.getVehicleId()));

        ServiceOrderEntity serviceOrder = null;
        if (request.getServiceOrderId() != null) {
            serviceOrder = serviceOrderRepository.findById(request.getServiceOrderId())
                    .orElseThrow(() -> new NotFoundException("Service order not found with id: " + request.getServiceOrderId()));
        }

        MaintenanceType maintenanceType = MaintenanceType.valueOf(request.getType());

        if (maintenanceType == MaintenanceType.MAINTENANCE && serviceOrder == null) {
            throw new BusinessException("Service order is required for MAINTENANCE type");
        }

        if (maintenanceType == MaintenanceType.MAINTENANCE && serviceOrder != null && serviceOrder.getStatus() != ServiceOrderStatus.APPROVED) {
            throw new BusinessException("Service order must be APPROVED for MAINTENANCE type");
        }

        if (maintenanceType != MaintenanceType.MAINTENANCE && request.getCostCenterId() == null && request.getProjectId() == null) {
            throw new BusinessException("At least one of costCenterId or projectId is required for " + maintenanceType + " type");
        }

        if (maintenanceType == MaintenanceType.FUEL) {
            validateFuelType(vehicle, request.getInvoiceFuelType());
        }

        MaintenanceEntity entity = maintenanceMapper.toEntity(request, vehicle, serviceOrder);
        entity.setMaintenanceStatus(MaintenanceStatus.PENDING);

        if (maintenanceType == MaintenanceType.FUEL && request.getLitersFilled() != null && request.getDistanceTraveled() != null) {
            boolean isAnomalous = checkAnomalousConsumption(vehicle, request.getLitersFilled(), request.getDistanceTraveled());
            entity.setAnomalousConsumption(isAnomalous);
        }

        MaintenanceEntity savedEntity = maintenanceRepository.save(entity);
        log.info("Maintenance record created with id: {}", savedEntity.getId());
        return maintenanceMapper.toResponseDTO(savedEntity);
    }

    private void validateFuelType(VehicleEntity vehicle, String invoiceFuelType) {
        if (invoiceFuelType == null || invoiceFuelType.isBlank()) {
            return;
        }

        FuelType vehicleFuelType = vehicle.getFuelType();
        FuelType invoiceFuel = FuelType.valueOf(invoiceFuelType);

        if (vehicleFuelType.equals(FuelType.ELECTRIC) && invoiceFuel != FuelType.ELECTRIC) {
            throw new BusinessException("Electric vehicle cannot use " + invoiceFuelType);
        }

        if (vehicleFuelType.equals(FuelType.HYBRID) && invoiceFuel != FuelType.HYBRID && invoiceFuel != FuelType.GASOLINE && invoiceFuel != FuelType.ETHANOL) {
            throw new BusinessException("Hybrid vehicle can only use HYBRID, GASOLINE or ETHANOL");
        }

        if (vehicleFuelType.equals(FuelType.GASOLINE) && invoiceFuel != FuelType.GASOLINE) {
            throw new BusinessException("Gasoline vehicle cannot use " + invoiceFuelType);
        }

        if (vehicleFuelType.equals(FuelType.DIESEL) && invoiceFuel != FuelType.DIESEL) {
            throw new BusinessException("Diesel vehicle cannot use " + invoiceFuelType);
        }

        if (vehicleFuelType.equals(FuelType.ETHANOL) && invoiceFuel != FuelType.ETHANOL) {
            throw new BusinessException("Ethanol vehicle cannot use " + invoiceFuelType);
        }

        if (vehicleFuelType.equals(FuelType.FLEX) && invoiceFuel != FuelType.GASOLINE && invoiceFuel != FuelType.ETHANOL) {
            throw new BusinessException("Flex vehicle can only use GASOLINE or ETHANOL");
        }
    }

    private boolean checkAnomalousConsumption(VehicleEntity vehicle, double litersFilled, double distanceTraveled) {
        if (distanceTraveled <= 0) {
            return false;
        }

        double currentConsumption = litersFilled / distanceTraveled;

        List<MaintenanceEntity> recentFuelRecords = maintenanceRepository
                .findTop10ByVehicleIdAndTypeOrderByMaintenanceDateDesc(vehicle.getId(), MaintenanceType.FUEL);

        if (recentFuelRecords.size() < 3) {
            return false;
        }

        double averageConsumption = recentFuelRecords.stream()
                .filter(m -> m.getLitersFilled() != null && m.getDistanceTraveled() != null && m.getDistanceTraveled() > 0)
                .mapToDouble(m -> m.getLitersFilled() / m.getDistanceTraveled())
                .average()
                .orElse(currentConsumption);

        return currentConsumption < averageConsumption * 0.8;
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

        ServiceOrderEntity serviceOrder = null;
        if (request.getServiceOrderId() != null) {
            serviceOrder = serviceOrderRepository.findById(request.getServiceOrderId())
                    .orElseThrow(() -> new NotFoundException("Service order not found with id: " + request.getServiceOrderId()));
        }

        maintenanceMapper.updateEntity(request, entity, vehicle, serviceOrder);
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
