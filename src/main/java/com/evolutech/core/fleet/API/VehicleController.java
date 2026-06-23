package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.VehicleService;
import com.evolutech.fleet.api.VehiclesApi;
import com.evolutech.fleet.api.model.VehicleDTO;
import com.evolutech.fleet.api.model.VehiclePageDTO;
import com.evolutech.fleet.api.model.VehicleRequestDTO;
import com.evolutech.fleet.api.model.UpdateVehicleStatusRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController implements VehiclesApi {

    private final VehicleService vehicleService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<VehicleDTO> createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        log.info("Creating vehicle with plate: {}", vehicleRequestDTO.getPlate());
        var internalRequest = apiMapper.toVehicleRequest(vehicleRequestDTO);
        var result = vehicleService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toVehicleApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(UUID id) {
        log.info("Deleting vehicle: {}", id);
        vehicleService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<VehiclePageDTO> getAllVehicles(String plate, String brand, String status, Integer page, Integer size) {
        log.info("Fetching vehicles - page: {}, size: {}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("plate").ascending());
        var vehicles = vehicleService.findByFilters(plate, brand,
                status != null ? com.evolutech.core.fleet.model.utils.enums.VehicleStatus.valueOf(status) : null,
                pageable);
        return ResponseEntity.ok(apiMapper.toVehiclePageApi(vehicles));
    }

    @Override
    public ResponseEntity<VehicleDTO> getVehicleById(UUID id) {
        log.info("Fetching vehicle: {}", id);
        return vehicleService.findById(id.toString())
                .map(v -> ResponseEntity.ok(apiMapper.toVehicleApi(v)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<VehicleDTO> updateVehicle(UUID id, VehicleRequestDTO vehicleRequestDTO) {
        log.info("Updating vehicle: {}", id);
        var internalRequest = apiMapper.toVehicleRequest(vehicleRequestDTO);
        var result = vehicleService.update(id.toString(), internalRequest);
        return ResponseEntity.ok(apiMapper.toVehicleApi(result));
    }

    @Override
    public ResponseEntity<VehicleDTO> updateVehicleStatus(UUID id, UpdateVehicleStatusRequestDTO updateVehicleStatusRequestDTO) {
        log.info("Updating status for vehicle: {}", id);
        var status = apiMapper.toVehicleStatus(updateVehicleStatusRequestDTO.getStatus());
        var result = vehicleService.updateStatus(id.toString(), status);
        return ResponseEntity.ok(apiMapper.toVehicleApi(result));
    }
}
