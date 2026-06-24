package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.TripRequestDTO;
import com.evolutech.core.fleet.model.dto.response.TripResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.TripStatus;
import com.evolutech.core.fleet.service.TripService;
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
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDTO> getTripById(@PathVariable String id) {
        log.info("Fetching trip: {}", id);
        return tripService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TripResponseDTO> createTrip(@Valid @RequestBody TripRequestDTO body) {
        log.info("Creating trip for vehicle: {} and driver: {}", body.getVehicleId(), body.getDriverId());
        var result = tripService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDTO> updateTrip(@PathVariable String id, @Valid @RequestBody TripRequestDTO body) {
        log.info("Updating trip: {}", id);
        var result = tripService.update(id, body);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<TripResponseDTO> startTrip(@PathVariable String id, @RequestBody Map<String, Double> body) {
        log.info("Starting trip: {}", id);
        var result = tripService.start(id, body.get("startMileage"));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TripResponseDTO> completeTrip(@PathVariable String id, @RequestBody Map<String, Double> body) {
        log.info("Completing trip: {}", id);
        var result = tripService.complete(id, body.get("endMileage"));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<TripResponseDTO> cancelTrip(@PathVariable String id) {
        log.info("Cancelling trip: {}", id);
        var result = tripService.cancel(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/deviation")
    public ResponseEntity<TripResponseDTO> registerDeviation(@PathVariable String id, @RequestBody Map<String, String> body) {
        log.info("Registering deviation for trip: {}", id);
        var result = tripService.registerDeviation(id, body.get("justification"));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable String id) {
        log.info("Deleting trip: {}", id);
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<TripResponseDTO>> getAllTrips(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) TripStatus status,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching trips - vehicle: {}, driver: {}, status: {}", vehicleId, driverId, status);
        if (vehicleId != null || driverId != null || status != null) {
            return ResponseEntity.ok(tripService.findByFilters(vehicleId, driverId, status, pageable));
        }
        return ResponseEntity.ok(tripService.findAllPaged(pageable));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Page<TripResponseDTO>> getTripsByVehicle(
            @PathVariable String vehicleId,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching trips by vehicle: {}", vehicleId);
        return ResponseEntity.ok(tripService.findByVehicleId(vehicleId, pageable));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Page<TripResponseDTO>> getTripsByDriver(
            @PathVariable String driverId,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching trips by driver: {}", driverId);
        return ResponseEntity.ok(tripService.findByDriverId(driverId, pageable));
    }
}
