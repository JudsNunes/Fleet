package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.TripRequestDTO;
import com.evolutech.core.fleet.model.dto.response.TripResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.entity.TripEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "vehicle", source = "vehicleEntity")
    @Mapping(target = "driver", source = "driverEntity")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "routeDeviation", ignore = true)
    TripEntity toEntity(TripRequestDTO request, VehicleEntity vehicleEntity, DriverEntity driverEntity);

    @Mapping(target = "vehicleId", expression = "java(entity.getVehicle() != null ? entity.getVehicle().getId() : null)")
    @Mapping(target = "driverId", expression = "java(entity.getDriver() != null ? entity.getDriver().getId() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    TripResponseDTO toResponseDTO(TripEntity entity);

    List<TripResponseDTO> toResponseDTOList(List<TripEntity> entities);
}
