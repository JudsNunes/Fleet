package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.MaintenanceService;
import com.evolutech.fleet.api.MaintenancesApi;
import com.evolutech.fleet.api.model.MaintenanceDTO;
import com.evolutech.fleet.api.model.MaintenancePageDTO;
import com.evolutech.fleet.api.model.MaintenanceRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MaintenanceController implements MaintenancesApi {

    private final MaintenanceService maintenanceService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<MaintenanceDTO> createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO) {
        log.info("Creating maintenance for vehicle: {}", maintenanceRequestDTO.getVehicleId());
        var internalRequest = apiMapper.toMaintenanceRequest(maintenanceRequestDTO);
        var result = maintenanceService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toMaintenanceApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteMaintenance(UUID id) {
        log.info("Deleting maintenance: {}", id);
        maintenanceService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<MaintenancePageDTO> getAllMaintenances(Integer page, Integer size) {
        log.info("Fetching all maintenances");
        var pageable = PageRequest.of(page, size, Sort.by("maintenanceDate").descending());
        var result = maintenanceService.findAllPaged(pageable);
        return ResponseEntity.ok(apiMapper.toMaintenancePageApi(result));
    }

    @Override
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByStatus(String status) {
        log.info("Fetching maintenances by status: {}", status);
        var result = maintenanceService.findByStatus(status);
        return ResponseEntity.ok(result.stream().map(apiMapper::toMaintenanceApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByVehicleId(UUID vehicleId) {
        log.info("Fetching maintenances for vehicle: {}", vehicleId);
        var result = maintenanceService.findByVehicleId(vehicleId.toString());
        return ResponseEntity.ok(result.stream().map(apiMapper::toMaintenanceApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(UUID id) {
        log.info("Fetching maintenance: {}", id);
        var result = maintenanceService.findById(id.toString());
        return ResponseEntity.ok(apiMapper.toMaintenanceApi(result));
    }

    @Override
    public ResponseEntity<Void> updateMaintenance(UUID id, MaintenanceRequestDTO maintenanceRequestDTO) {
        log.info("Updating maintenance: {}", id);
        var internalRequest = apiMapper.toMaintenanceRequest(maintenanceRequestDTO);
        maintenanceService.update(id.toString(), internalRequest);
        return ResponseEntity.ok().build();
    }
}
