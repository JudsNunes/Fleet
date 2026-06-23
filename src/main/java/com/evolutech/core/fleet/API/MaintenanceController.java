package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.MaintenanceRequestDTO;
import com.evolutech.core.fleet.model.dto.response.MaintenanceResponseDTO;
import com.evolutech.core.fleet.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Slf4j
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> createMaintenance(@Valid @RequestBody MaintenanceRequestDTO request) {
        log.info("Creating maintenance for vehicle: {}", request.getVehicleId());
        var response = maintenanceService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> getMaintenanceById(@PathVariable String id) {
        log.info("Fetching maintenance: {}", id);
        var response = maintenanceService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<MaintenanceResponseDTO>> getAllMaintenances(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching maintenances - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        var maintenances = maintenanceService.findAllPaged(pageable);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenancesByVehicleId(@PathVariable String vehicleId) {
        log.info("Fetching maintenances for vehicle: {}", vehicleId);
        var maintenances = maintenanceService.findByVehicleId(vehicleId);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenancesByStatus(@PathVariable String status) {
        log.info("Fetching maintenances with status: {}", status);
        var maintenances = maintenanceService.findByStatus(status);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/vehicle/{vehicleId}/status/{status}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenancesByVehicleIdAndStatus(
            @PathVariable String vehicleId,
            @PathVariable String status) {
        log.info("Fetching maintenances for vehicle: {} with status: {}", vehicleId, status);
        var maintenances = maintenanceService.findByVehicleIdAndStatus(vehicleId, status);
        return ResponseEntity.ok(maintenances);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDTO> updateMaintenance(
            @PathVariable String id,
            @Valid @RequestBody MaintenanceRequestDTO request) {
        log.info("Updating maintenance: {}", id);
        var response = maintenanceService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable String id) {
        log.info("Deleting maintenance: {}", id);
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
