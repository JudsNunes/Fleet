package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.FineRequestDTO;
import com.evolutech.core.fleet.model.dto.response.FineResponseDTO;
import com.evolutech.core.fleet.model.entity.FineEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FineMapper {

    @Mapping(target = "vehicle", source = "vehicleEntity")
    @Mapping(target = "status", expression = "java(com.evolutech.core.fleet.model.utils.enums.FineStatus.valueOf(request.getStatus()))")
    FineEntity toEntity(FineRequestDTO request, VehicleEntity vehicleEntity);

    @Mapping(target = "vehicleId", expression = "java(entity.getVehicle() != null ? entity.getVehicle().getId() : null)")
    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    FineResponseDTO toResponseDTO(FineEntity entity);

    List<FineResponseDTO> toResponseDTOList(List<FineEntity> entities);
}
