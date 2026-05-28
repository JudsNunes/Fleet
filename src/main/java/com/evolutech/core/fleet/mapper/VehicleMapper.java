package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleEntity toEntity(VehicleRequestDTO request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    VehicleResponseDTO toResponseDTO(VehicleEntity vehicleEntity);

    List<VehicleResponseDTO> toResponseDTOList(List<VehicleEntity> vehicleEntities);
}

