package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.response.AuditLogResponseDTO;
import com.evolutech.core.fleet.model.dto.response.FineResponseDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.model.dto.response.ServiceOrderResponseDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApiMapper {

    public com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO toVehicleRequest(com.evolutech.fleet.api.model.VehicleRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.builder()
                .plate(api.getPlate())
                .model(api.getModel())
                .brand(api.getBrand())
                .year(api.getYear())
                .color(api.getColor())
                .mileage(api.getMileage())
                .chassis(api.getChassis())
                .renavam(api.getRenavam())
                .fuelType(api.getFuelType() != null ? api.getFuelType().getValue() : null)
                .cargoCapacityKg(api.getCargoCapacityKg())
                .passengerCapacity(api.getPassengerCapacity())
                .engineType(api.getEngineType())
                .build();
    }

    public com.evolutech.fleet.api.model.VehicleDTO toVehicleApi(VehicleResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.VehicleDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setPlate(internal.getPlate());
        dto.setModel(internal.getModel());
        dto.setBrand(internal.getBrand());
        dto.setYear(internal.getYear());
        dto.setColor(internal.getColor());
        dto.setMileage(internal.getMileage());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.VehicleDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setChassis(internal.getChassis());
        dto.setRenavam(internal.getRenavam());
        dto.setFuelType(internal.getFuelType() != null ? com.evolutech.fleet.api.model.VehicleDTO.FuelTypeEnum.fromValue(internal.getFuelType()) : null);
        dto.setCargoCapacityKg(internal.getCargoCapacityKg());
        dto.setPassengerCapacity(internal.getPassengerCapacity());
        dto.setEngineType(internal.getEngineType());
        dto.setCreatedAt(internal.getCreatedAt());
        dto.setUpdatedAt(internal.getUpdatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.VehiclePageDTO toVehiclePageApi(Page<VehicleResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.VehiclePageDTO();
        dto.setContent(page.getContent().stream().map(this::toVehicleApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public VehicleStatus toVehicleStatus(com.evolutech.fleet.api.model.UpdateVehicleStatusRequestDTO.StatusEnum status) {
        return VehicleStatus.valueOf(status.getValue());
    }

    public com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO toMaintenanceRequest(com.evolutech.fleet.api.model.MaintenanceRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO.builder()
                .vehicleId(api.getVehicleId() != null ? api.getVehicleId().toString() : null)
                .description(api.getDescription())
                .type(api.getType() != null ? api.getType().getValue() : null)
                .cost(api.getCost())
                .mileage(api.getMileage())
                .nextMileage(api.getNextMileage())
                .maintenanceDate(api.getMaintenanceDate())
                .build();
    }

    public com.evolutech.fleet.api.model.MaintenanceDTO toMaintenanceApi(MaintenanceResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.MaintenanceDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setVehicleId(internal.getVehicleId() != null ? UUID.fromString(internal.getVehicleId()) : null);
        dto.setDescription(internal.getDescription());
        dto.setType(internal.getType() != null ? com.evolutech.fleet.api.model.MaintenanceDTO.TypeEnum.fromValue(internal.getType()) : null);
        dto.setCost(internal.getCost());
        dto.setMileage(internal.getMileage());
        dto.setNextMileage(internal.getNextMileage());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.MaintenanceDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setMaintenanceDate(internal.getMaintenanceDate());
        dto.setCreatedAt(internal.getCreatedAt());
        dto.setUpdatedAt(internal.getUpdatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.MaintenancePageDTO toMaintenancePageApi(Page<MaintenanceResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.MaintenancePageDTO();
        dto.setContent(page.getContent().stream().map(this::toMaintenanceApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public com.evolutech.core.fleet.model.dto.request.ServiceOrderRequestDTO toServiceOrderRequest(com.evolutech.fleet.api.model.ServiceOrderRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.ServiceOrderRequestDTO.builder()
                .vehicleId(api.getVehicleId() != null ? api.getVehicleId().toString() : null)
                .description(api.getDescription())
                .warrantyExpiryDate(api.getWarrantyExpiryDate())
                .build();
    }

    public com.evolutech.fleet.api.model.ServiceOrderDTO toServiceOrderApi(ServiceOrderResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.ServiceOrderDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setVehicleId(internal.getVehicleId() != null ? UUID.fromString(internal.getVehicleId()) : null);
        dto.setDescription(internal.getDescription());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.ServiceOrderDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setApprovedBy(internal.getApprovedBy());
        dto.setApprovedAt(internal.getApprovedAt());
        dto.setWarrantyExpiryDate(internal.getWarrantyExpiryDate());
        dto.setCreatedAt(internal.getCreatedAt());
        dto.setUpdatedAt(internal.getUpdatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.ServiceOrderPageDTO toServiceOrderPageApi(Page<ServiceOrderResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.ServiceOrderPageDTO();
        dto.setContent(page.getContent().stream().map(this::toServiceOrderApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public com.evolutech.core.fleet.model.dto.request.ServiceOrderApprovalDTO toServiceOrderApproval(com.evolutech.fleet.api.model.ServiceOrderApprovalDTO api) {
        return com.evolutech.core.fleet.model.dto.request.ServiceOrderApprovalDTO.builder()
                .approvedBy(api.getApprovedBy())
                .build();
    }

    public com.evolutech.fleet.api.model.AuditLogDTO toAuditLogApiFromInternal(AuditLogResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.AuditLogDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setEntityType(internal.getEntityType());
        dto.setEntityId(internal.getEntityId() != null ? UUID.fromString(internal.getEntityId()) : null);
        dto.setAction(internal.getAction() != null ? com.evolutech.fleet.api.model.AuditLogDTO.ActionEnum.fromValue(internal.getAction()) : null);
        dto.setFieldName(internal.getFieldName());
        dto.setOldValue(internal.getOldValue());
        dto.setNewValue(internal.getNewValue());
        dto.setUserId(internal.getUserId());
        dto.setTimestamp(internal.getTimestamp());
        dto.setIpAddress(internal.getIpAddress());
        dto.setCreatedAt(internal.getCreatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.AuditLogPageDTO toAuditLogPageApi(Page<AuditLogResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.AuditLogPageDTO();
        dto.setContent(page.getContent().stream().map(this::toAuditLogApiFromInternal).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public com.evolutech.core.fleet.model.dto.request.FineRequestDTO toFineRequest(com.evolutech.fleet.api.model.FineRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.FineRequestDTO.builder()
                .vehicleId(api.getVehicleId() != null ? api.getVehicleId().toString() : null)
                .driverCpf(api.getDriverCpf())
                .description(api.getDescription())
                .amount(api.getAmount())
                .infractionDate(api.getInfractionDate())
                .points(api.getPoints())
                .status(api.getStatus() != null ? api.getStatus().getValue() : null)
                .costCenterId(api.getCostCenterId())
                .build();
    }

    public com.evolutech.fleet.api.model.FineDTO toFineApi(FineResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.FineDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setVehicleId(internal.getVehicleId() != null ? UUID.fromString(internal.getVehicleId()) : null);
        dto.setDriverCpf(internal.getDriverCpf());
        dto.setDescription(internal.getDescription());
        dto.setAmount(internal.getAmount());
        dto.setInfractionDate(internal.getInfractionDate());
        dto.setPoints(internal.getPoints());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.FineDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setCostCenterId(internal.getCostCenterId());
        dto.setCreatedAt(internal.getCreatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.FinePageDTO toFinePageApi(Page<FineResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.FinePageDTO();
        dto.setContent(page.getContent().stream().map(this::toFineApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public com.evolutech.core.fleet.model.dto.request.DriverRequestDTO toDriverRequestInternal(com.evolutech.fleet.api.model.DriverRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.DriverRequestDTO.builder()
                .name(api.getName())
                .cpf(api.getCpf())
                .cnhNumber(api.getCnhNumber())
                .cnhCategory(api.getCnhCategory() != null ? api.getCnhCategory().getValue() : null)
                .cnhExpiryDate(api.getCnhExpiryDate())
                .phone(api.getPhone())
                .email(api.getEmail())
                .birthDate(api.getBirthDate())
                .address(api.getAddress())
                .build();
    }

    public com.evolutech.fleet.api.model.DriverDTO toDriverApi(com.evolutech.core.fleet.model.dto.response.DriverResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.DriverDTO();
        dto.setId(internal.getId() != null ? UUID.fromString(internal.getId()) : null);
        dto.setName(internal.getName());
        dto.setCpf(internal.getCpf());
        dto.setCnhNumber(internal.getCnhNumber());
        dto.setCnhCategory(internal.getCnhCategory() != null ? com.evolutech.fleet.api.model.DriverDTO.CnhCategoryEnum.fromValue(internal.getCnhCategory()) : null);
        dto.setCnhExpiryDate(internal.getCnhExpiryDate());
        dto.setCnhStatus(internal.getCnhStatus() != null ? com.evolutech.fleet.api.model.DriverDTO.CnhStatusEnum.fromValue(internal.getCnhStatus()) : null);
        dto.setPhone(internal.getPhone());
        dto.setEmail(internal.getEmail());
        dto.setBirthDate(internal.getBirthDate());
        dto.setAddress(internal.getAddress());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.DriverDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setCreatedAt(internal.getCreatedAt());
        dto.setUpdatedAt(internal.getUpdatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.DriverPageDTO toDriverPageApi(Page<com.evolutech.core.fleet.model.dto.response.DriverResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.DriverPageDTO();
        dto.setContent(page.getContent().stream().map(this::toDriverApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }

    public com.evolutech.core.fleet.model.dto.request.TripRequestDTO toTripRequestInternal(com.evolutech.fleet.api.model.TripRequestDTO api) {
        return com.evolutech.core.fleet.model.dto.request.TripRequestDTO.builder()
                .vehicleId(api.getVehicleId() != null ? api.getVehicleId().toString() : null)
                .driverId(api.getDriverId() != null ? api.getDriverId().toString() : null)
                .description(api.getDescription())
                .origin(api.getOrigin())
                .destination(api.getDestination())
                .plannedDistanceKm(api.getPlannedDistanceKm())
                .departureDate(api.getDepartureDate())
                .startMileage(api.getStartMileage())
                .build();
    }

    public com.evolutech.fleet.api.model.TripDTO toTripApi(com.evolutech.core.fleet.model.dto.response.TripResponseDTO internal) {
        var dto = new com.evolutech.fleet.api.model.TripDTO();
        dto.setId(internal.getId() != null ? java.util.UUID.fromString(internal.getId()) : null);
        dto.setVehicleId(internal.getVehicleId() != null ? java.util.UUID.fromString(internal.getVehicleId()) : null);
        dto.setDriverId(internal.getDriverId() != null ? java.util.UUID.fromString(internal.getDriverId()) : null);
        dto.setDescription(internal.getDescription());
        dto.setOrigin(internal.getOrigin());
        dto.setDestination(internal.getDestination());
        dto.setPlannedDistanceKm(internal.getPlannedDistanceKm());
        dto.setActualDistanceKm(internal.getActualDistanceKm());
        dto.setDepartureDate(internal.getDepartureDate());
        dto.setArrivalDate(internal.getArrivalDate());
        dto.setStartMileage(internal.getStartMileage());
        dto.setEndMileage(internal.getEndMileage());
        dto.setStatus(internal.getStatus() != null ? com.evolutech.fleet.api.model.TripDTO.StatusEnum.fromValue(internal.getStatus()) : null);
        dto.setRouteDeviation(internal.getRouteDeviation());
        dto.setDeviationJustification(internal.getDeviationJustification());
        dto.setCreatedAt(internal.getCreatedAt());
        dto.setUpdatedAt(internal.getUpdatedAt());
        return dto;
    }

    public com.evolutech.fleet.api.model.TripPageDTO toTripPageApi(org.springframework.data.domain.Page<com.evolutech.core.fleet.model.dto.response.TripResponseDTO> page) {
        var dto = new com.evolutech.fleet.api.model.TripPageDTO();
        dto.setContent(page.getContent().stream().map(this::toTripApi).collect(Collectors.toList()));
        dto.setTotalElements((int) page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setSize(page.getSize());
        dto.setNumber(page.getNumber());
        return dto;
    }
}
