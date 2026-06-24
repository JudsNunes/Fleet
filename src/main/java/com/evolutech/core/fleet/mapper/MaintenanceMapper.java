package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", source = "vehicleEntity")
    @Mapping(target = "serviceOrder", source = "serviceOrderEntity")
    @Mapping(target = "maintenanceDate", source = "request.maintenanceDate")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "type", expression = "java(com.evolutech.core.fleet.model.utils.enums.MaintenanceType.valueOf(request.getType()))")
    @Mapping(target = "cost", source = "request.cost")
    @Mapping(target = "mileage", source = "request.mileage")
    @Mapping(target = "nextMileage", source = "request.nextMileage")
    @Mapping(target = "maintenanceStatus", ignore = true)
    @Mapping(target = "costCenterId", source = "request.costCenterId")
    @Mapping(target = "projectId", source = "request.projectId")
    @Mapping(target = "invoiceFuelType", source = "request.invoiceFuelType")
    @Mapping(target = "litersFilled", source = "request.litersFilled")
    @Mapping(target = "distanceTraveled", source = "request.distanceTraveled")
    @Mapping(target = "anomalousConsumption", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    MaintenanceEntity toEntity(MaintenanceRequestDTO request, VehicleEntity vehicleEntity, ServiceOrderEntity serviceOrderEntity);

    @Mapping(target = "maintenanceDate", source = "maintenanceDate")
    @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().name() : null)")
    @Mapping(target = "status", expression = "java(entity.getMaintenanceStatus() != null ? entity.getMaintenanceStatus().name() : null)")
    @Mapping(target = "vehicleId", expression = "java(entity.getVehicle() != null ? entity.getVehicle().getId() : null)")
    @Mapping(target = "serviceOrderId", expression = "java(entity.getServiceOrder() != null ? entity.getServiceOrder().getId() : null)")
    MaintenanceResponseDTO toResponseDTO(MaintenanceEntity entity);

    List<MaintenanceResponseDTO> toResponseDTOList(List<MaintenanceEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", source = "vehicleEntity")
    @Mapping(target = "serviceOrder", source = "serviceOrderEntity")
    @Mapping(target = "maintenanceDate", source = "request.maintenanceDate")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "type", expression = "java(com.evolutech.core.fleet.model.utils.enums.MaintenanceType.valueOf(request.getType()))")
    @Mapping(target = "cost", source = "request.cost")
    @Mapping(target = "mileage", source = "request.mileage")
    @Mapping(target = "nextMileage", source = "request.nextMileage")
    @Mapping(target = "maintenanceStatus", ignore = true)
    @Mapping(target = "costCenterId", source = "request.costCenterId")
    @Mapping(target = "projectId", source = "request.projectId")
    @Mapping(target = "invoiceFuelType", source = "request.invoiceFuelType")
    @Mapping(target = "litersFilled", source = "request.litersFilled")
    @Mapping(target = "distanceTraveled", source = "request.distanceTraveled")
    @Mapping(target = "anomalousConsumption", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntity(MaintenanceRequestDTO request, @MappingTarget MaintenanceEntity entity, VehicleEntity vehicleEntity, ServiceOrderEntity serviceOrderEntity);
}
