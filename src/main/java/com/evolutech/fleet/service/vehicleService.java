package com.evolutech.fleet.service;

import com.evolutech.fleet.entity.Vehicle;

import java.util.List;

public interface vehicleService {

    Vehicle saveByEntity(Vehicle vehicle);

    Vehicle findByIdAndReturnEntity(Long id);

    List<Vehicle> findAllAndReturnListOfEntity(Vehicle vehicle);

    Vehicle updateByEntity(Vehicle vehicle);

    Vehicle deleteByEntity(Vehicle vehicle);

    List<Vehicle> findByIdAndPlate(Long id, String plate);

}
