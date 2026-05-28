package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.api.VehiclesApi;
import com.evolutech.core.fleet.api.model.VehicleDTO;
import com.evolutech.core.fleet.api.model.VehicleRequestDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController implements VehiclesApi {

    private final VehicleService vehicleService;

    /**
     * POST /vehicles - Criar um novo veículo
     * Status: 201 (Created), 400 (Bad Request), 409 (Conflict), 500 (Server Error)
     */
    @Override
    public ResponseEntity<VehicleDTO> createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        log.info("Creating new vehicle with plate: {}", vehicleRequestDTO.getPlate());
        try {
            var responseDTO = vehicleService.save(convertRequestToServiceDTO(vehicleRequestDTO));
            var vehicleDTO = convertResponseToApiDTO(responseDTO);
            log.debug("Vehicle created successfully with ID: {}", vehicleDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDTO);
        } catch (BusinessException e) {
            log.error("Business error creating vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }



    /**
     * GET /vehicles/{id} - Buscar veículo por ID
     * Status: 200 (OK), 404 (Not Found), 500 (Server Error)
     */
    @Override
    public ResponseEntity<VehicleDTO> getVehicleById(Long id) {
        log.info("Fetching vehicle with ID: {}", id);
        try {
            var vehicle = vehicleService.findById(id);
            if (vehicle.isPresent()) {
                var vehicleDTO = convertResponseToApiDTO(vehicle.get());
                log.debug("Vehicle found with ID: {}", id);
                return ResponseEntity.ok(vehicleDTO);
            }
            log.warn("Vehicle not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error fetching vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /vehicles - Listar todos os veículos
     * Status: 200 (OK), 500 (Server Error)
     */
    @Override
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        log.info("Fetching all vehicles");
        try {
            var vehicles = vehicleService.findAll();
            var vehicleDTOs = vehicles.stream()
                    .map(this::convertResponseToApiDTO)
                    .toList();
            log.debug("Found {} vehicles", vehicleDTOs.size());
            return ResponseEntity.ok(vehicleDTOs);
        } catch (Exception e) {
            log.error("Error fetching all vehicles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /vehicles/{id} - Atualizar um veículo
     * Status: 200 (OK), 400 (Bad Request), 404 (Not Found), 500 (Server Error)
     */
    @Override
    public ResponseEntity<VehicleDTO> updateVehicle(Long id, VehicleRequestDTO vehicleRequestDTO) {
        log.info("Updating vehicle with ID: {}", id);
        try {
            // Verificar se veículo existe
            if (vehicleService.findById(id).isEmpty()) {
                log.warn("Vehicle not found with ID: {} for update", id);
                return ResponseEntity.notFound().build();
            }

            var serviceDTO = convertRequestToServiceDTO(vehicleRequestDTO);
            var responseDTO = vehicleService.update(serviceDTO);
            var vehicleDTO = convertResponseToApiDTO(responseDTO);
            log.debug("Vehicle with ID: {} updated successfully", id);
            return ResponseEntity.ok(vehicleDTO);
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for vehicle update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (BusinessException e) {
            log.error("Business error updating vehicle: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /vehicles/{id} - Deletar um veículo
     * Status: 204 (No Content), 404 (Not Found), 500 (Server Error)
     */
    @Override
    public ResponseEntity<Void> deleteVehicle(Long id) {
        log.info("Deleting vehicle with ID: {}", id);
        try {
            vehicleService.delete(id);
            log.debug("Vehicle with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            log.warn("Vehicle not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /vehicles/search - Buscar veículo por ID e placa
     * Status: 200 (OK), 404 (Not Found), 500 (Server Error)
     */
    @Override
    public ResponseEntity<List<VehicleDTO>> findByIdAndPlate(Long id, String plate) {
        log.info("Searching vehicle by ID: {} and plate: {}", id, plate);
        try {
            var vehicle = vehicleService.findById(id);
            if (vehicle.isPresent() && vehicle.get().getPlate().equalsIgnoreCase(plate)) {
                log.debug("Vehicle found with ID: {} and plate: {}", id, plate);
                var vehicleDTO = convertResponseToApiDTO(vehicle.get());
                return ResponseEntity.ok(List.of(vehicleDTO));
            }
            log.warn("Vehicle not found with ID: {} and plate: {}", id, plate);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error searching vehicle with ID: {} and plate: {}: {}", id, plate, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ==================== MÉTODOS AUXILIARES DE CONVERSÃO ====================

    /**
     * Converte VehicleRequestDTO (API) para VehicleRequestDTO (Service)
     */
    private com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO convertRequestToServiceDTO(
            VehicleRequestDTO apiDTO) {
        return com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO.builder()
                .plate(apiDTO.getPlate())
                .model(apiDTO.getModel())
                .brand(apiDTO.getBrand())
                .year(apiDTO.getYear())
                .color(apiDTO.getColor())
                .mileage(apiDTO.getMileage())
                .build();
    }

    /**
     * Converte VehicleResponseDTO (Service) para VehicleDTO (API)
     */
}
