package com.evolutech.fleet.service;

import com.evolutech.fleet.api.model.VehicleDTO;
import com.evolutech.fleet.api.model.VehicleRequestDTO;

import java.util.List;

public interface VehicleService {

    VehicleDTO saveByEntity(VehicleRequestDTO vehicleRequestDTO);

    VehicleDTO findByIdAndReturnEntity(Long id);

    List<VehicleDTO> findAllAndReturnListOfEntity();

    VehicleDTO updateByEntity(VehicleRequestDTO vehicleRequestDTO);

    Void deleteByEntity(VehicleRequestDTO vehicleRequestDTO);

    List<VehicleDTO> findByIdAndPlate(Long id, String plate);

}
