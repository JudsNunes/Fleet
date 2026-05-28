package com.evolutech.core.fleet.mapper;

import com.evolutech.core.fleet.api.model.VehicleRequestDTO;
import com.evolutech.core.fleet.api.model.VehicleResponseDTO;
import com.evolutech.core.fleet.model.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequestDTO request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    VehicleResponseDTO toResponseDTO(Vehicle vehicle);

    List<VehicleResponseDTO> toResponseDTOList(List<Vehicle> vehicles);
}

