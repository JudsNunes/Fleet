package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.TripService;
import com.evolutech.fleet.api.TripsApi;
import com.evolutech.fleet.api.model.TripDTO;
import com.evolutech.fleet.api.model.TripPageDTO;
import com.evolutech.fleet.api.model.TripRequestDTO;
import com.evolutech.fleet.api.model.StartTripRequestDTO;
import com.evolutech.fleet.api.model.CompleteTripRequestDTO;
import com.evolutech.fleet.api.model.RegisterDeviationRequestDTO;
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
public class TripController implements TripsApi {

    private final TripService tripService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<TripDTO> createTrip(TripRequestDTO tripRequestDTO) {
        log.info("Creating trip for vehicle: {} and driver: {}", tripRequestDTO.getVehicleId(), tripRequestDTO.getDriverId());
        var internalRequest = apiMapper.toTripRequestInternal(tripRequestDTO);
        var result = tripService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteTrip(UUID id) {
        log.info("Deleting trip: {}", id);
        tripService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TripPageDTO> getAllTrips(UUID vehicleId, UUID driverId, String status, Integer page, Integer size) {
        log.info("Fetching trips - page: {}, size: {}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("departureDate").descending());
        var trips = tripService.findByFilters(
                vehicleId != null ? vehicleId.toString() : null,
                driverId != null ? driverId.toString() : null,
                status != null ? com.evolutech.core.fleet.model.utils.enums.TripStatus.valueOf(status) : null,
                pageable);
        return ResponseEntity.ok(apiMapper.toTripPageApi(trips));
    }

    @Override
    public ResponseEntity<TripDTO> getTripById(UUID id) {
        log.info("Fetching trip: {}", id);
        return tripService.findById(id.toString())
                .map(t -> ResponseEntity.ok(apiMapper.toTripApi(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<TripDTO> updateTrip(UUID id, TripRequestDTO tripRequestDTO) {
        log.info("Updating trip: {}", id);
        var internalRequest = apiMapper.toTripRequestInternal(tripRequestDTO);
        var result = tripService.update(id.toString(), internalRequest);
        return ResponseEntity.ok(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<TripDTO> startTrip(UUID id, StartTripRequestDTO startTripRequestDTO) {
        log.info("Starting trip: {}", id);
        var result = tripService.start(id.toString(), startTripRequestDTO.getStartMileage());
        return ResponseEntity.ok(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<TripDTO> completeTrip(UUID id, CompleteTripRequestDTO completeTripRequestDTO) {
        log.info("Completing trip: {}", id);
        var result = tripService.complete(id.toString(), completeTripRequestDTO.getEndMileage());
        return ResponseEntity.ok(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<TripDTO> cancelTrip(UUID id) {
        log.info("Cancelling trip: {}", id);
        var result = tripService.cancel(id.toString());
        return ResponseEntity.ok(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<TripDTO> registerDeviation(UUID id, RegisterDeviationRequestDTO registerDeviationRequestDTO) {
        log.info("Registering deviation for trip: {}", id);
        var result = tripService.registerDeviation(id.toString(), registerDeviationRequestDTO.getJustification());
        return ResponseEntity.ok(apiMapper.toTripApi(result));
    }

    @Override
    public ResponseEntity<TripPageDTO> getTripsByVehicle(UUID vehicleId, Integer page, Integer size) {
        log.info("Fetching trips by vehicle: {}", vehicleId);
        var pageable = PageRequest.of(page, size, Sort.by("departureDate").descending());
        var result = tripService.findByVehicleId(vehicleId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toTripPageApi(result));
    }

    @Override
    public ResponseEntity<TripPageDTO> getTripsByDriver(UUID driverId, Integer page, Integer size) {
        log.info("Fetching trips by driver: {}", driverId);
        var pageable = PageRequest.of(page, size, Sort.by("departureDate").descending());
        var result = tripService.findByDriverId(driverId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toTripPageApi(result));
    }
}
