package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import com.evolutech.core.fleet.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO request) {
        log.info("Creating vehicle with plate: {}", request.getPlate());
        var response = vehicleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable String id) {
        log.info("Fetching vehicle: {}", id);
        return vehicleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching vehicles - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        var vehicles = vehicleService.findAllPaged(pageable);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VehicleResponseDTO>> searchVehicles(
            @RequestParam(required = false) String plate,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) VehicleStatus status,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Searching vehicles - plate: {}, brand: {}, status: {}", plate, brand, status);
        var vehicles = vehicleService.findByFilters(plate, brand, status, pageable);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody VehicleRequestDTO request) {
        log.info("Updating vehicle: {}", id);
        var response = vehicleService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VehicleResponseDTO> updateVehicleStatus(
            @PathVariable String id,
            @RequestBody Map<String, VehicleStatus> statusRequest) {
        log.info("Updating status for vehicle: {}", id);
        var response = vehicleService.updateStatus(id, statusRequest.get("status"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        log.info("Deleting vehicle: {}", id);
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
