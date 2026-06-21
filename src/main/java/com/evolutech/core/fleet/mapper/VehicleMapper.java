package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.fleet.api.model.VehicleDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleEntity toEntity(VehicleRequestDTO request);

    VehicleResponseDTO toResponseDTO(VehicleEntity vehicleEntity);

    List<VehicleResponseDTO> toResponseDTOList(List<VehicleEntity> vehicleEntities);

    VehicleDTO toVehicleDTO(VehicleEntity vehicleEntity);

}

