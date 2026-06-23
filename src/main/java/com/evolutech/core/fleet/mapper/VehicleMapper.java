package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "status", ignore = true)
    VehicleEntity toEntity(VehicleRequestDTO request);

    @Mapping(target = "status", expression = "java(vehicleEntity.getStatus() != null ? vehicleEntity.getStatus().name() : null)")
    VehicleResponseDTO toResponseDTO(VehicleEntity vehicleEntity);

    List<VehicleResponseDTO> toResponseDTOList(List<VehicleEntity> vehicleEntities);
}
