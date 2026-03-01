package com.evolutech.fleet.mapper;

import com.evolutech.fleet.api.model.VehicleDTO;
import com.evolutech.fleet.model.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    VehicleDTO vehicleToVehicleDTO(Vehicle vehicle);

    Vehicle toDTO(VehicleDTO vehicleDTO);
}
