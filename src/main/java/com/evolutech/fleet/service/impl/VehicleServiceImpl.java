package com.evolutech.fleet.service.impl;

import com.evolutech.fleet.entity.Vehicle;
import com.evolutech.fleet.exception.BusinessException;
import com.evolutech.fleet.repository.VehicleRepository;
import com.evolutech.fleet.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class VehicleServiceImpl implements VehicleService {

    private static final String VEHICLE_NOT_FOUND = "Vehicle not found with id: ";
    private static final String VEHICLE_ALREADY_EXISTS = "Vehicle already exists with id: ";
    private static final String ERROR_SAVING_VEHICLE = "Error saving vehicle with id: ";
    private static final String ERROR_DELETING_VEHICLE = "Error deleting vehicle with id: ";
    private static final String SUCCESS_UPDATE_VEHICLE = "Successfully updated vehicle with id: ";
    private static final String ERROR_UPDATING_VEHICLE = "Vehicle not found with id: ";
    private static final String SUCCESS_SAVING_VEHICLE = "Vehicle saved with id: ";



    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle findByIdAndReturnEntity(Long id) {
        log.info("Finding vehicle with id: {}", id);
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new BusinessException(VEHICLE_NOT_FOUND + id));
    }

    @Override
    public Vehicle saveByEntity(Vehicle vehicle) {
        log.info("Saving vehicle with id: {}", vehicle.id());
        try {
            vehicleRepository.findById(vehicle.id()).ifPresent(existingVehicle -> {
                log.error(VEHICLE_ALREADY_EXISTS + "{}", vehicle.id());
                throw new BusinessException(VEHICLE_ALREADY_EXISTS + vehicle.id());
            });
            Vehicle saved = vehicleRepository.save(vehicle);
            log.debug(SUCCESS_SAVING_VEHICLE + "{}", vehicle.id());
            return saved;
        } catch (BusinessException e) {
            log.error(ERROR_SAVING_VEHICLE + "{}", vehicle.id(), e);
            throw e;
        }
    }

    @Override
    public Void deleteByEntity(Vehicle vehicle) {
        log.info("Deleting vehicle with id: {}", vehicle.id());
        try {
            Vehicle existingVehicle = vehicleRepository.findById(vehicle.id())
                .orElseThrow(() -> new BusinessException(VEHICLE_NOT_FOUND + vehicle.id()));
            vehicleRepository.delete(existingVehicle);
            log.debug("Vehicle successfully deleted with id: {}", vehicle.id());
            return null;
        } catch (BusinessException e) {
            log.error(ERROR_DELETING_VEHICLE + "{}", vehicle.id(), e);
            throw e;
        }
    }


    @Override
    public Vehicle updateByEntity(Vehicle vehicle){
        log.info("--- Calling [VehicleService] Update Vehicle with id: {} ---",vehicle.id());
        if(vehicleRepository.existsById(vehicle.id()) || vehicleRepository.findById(vehicle.id()).isPresent()) {
            log.debug(" --- Successfully Updating Vehicle with id: {} --- ", vehicle.id());
            return vehicleRepository.save(vehicle);
        } else {
            log.error(" --- Vehicle not found With id: {} ---",vehicle.id());
            throw new BusinessException(ERROR_UPDATING_VEHICLE);
        }
    }

    @Override
    public List<Vehicle> findAllAndReturnListOfEntity(){
        log.info("Finding all vehicles with Sort");
        Sort sort = Sort.by("id").ascending().and(Sort.by("Name").ascending());
        return vehicleRepository.findAll(sort);
    }


    @Override
    public List<Vehicle> findByIdAndPlate(Long id, String plate){
        log.info(" --- Finding vehicle with id: {} and plate: {} --- ", id, plate);

        if(findByIdAndReturnEntity(id) != null || Objects.nonNull(plate)) {
            log.debug(" --- Successfully found vehicle with id: {} and plate: {} --- ", id, plate);
            return vehicleRepository.findByIdAndPlate(id, plate);
        }
        log.error(" --- Vehicle not found with id: {} and plate: {} --- ", id, plate);
        throw new BusinessException(VEHICLE_NOT_FOUND + id + " and plate: " + plate);
    }

}
