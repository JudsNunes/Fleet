package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.VehicleAssignmentRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleAssignmentResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.entity.VehicleAssignmentEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleAssignmentMapper {

    @Mapping(target = "vehicle", source = "vehicleEntity")
    @Mapping(target = "driver", source = "driverEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    VehicleAssignmentEntity toEntity(VehicleAssignmentRequestDTO request, VehicleEntity vehicleEntity, DriverEntity driverEntity);

    @Mapping(target = "vehicleId", expression = "java(entity.getVehicle() != null ? entity.getVehicle().getId() : null)")
    @Mapping(target = "driverId", expression = "java(entity.getDriver() != null ? entity.getDriver().getId() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    VehicleAssignmentResponseDTO toResponseDTO(VehicleAssignmentEntity entity);

    List<VehicleAssignmentResponseDTO> toResponseDTOList(List<VehicleAssignmentEntity> entities);
}
