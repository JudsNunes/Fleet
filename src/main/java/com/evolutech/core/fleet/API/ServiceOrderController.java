package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import com.evolutech.core.fleet.service.ServiceOrderService;
import com.evolutech.fleet.api.ServiceOrdersApi;
import com.evolutech.fleet.api.model.ServiceOrderApprovalDTO;
import com.evolutech.fleet.api.model.ServiceOrderDTO;
import com.evolutech.fleet.api.model.ServiceOrderPageDTO;
import com.evolutech.fleet.api.model.ServiceOrderRequestDTO;
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
public class ServiceOrderController implements ServiceOrdersApi {

    private final ServiceOrderService serviceOrderService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<ServiceOrderDTO> approveServiceOrder(UUID id, ServiceOrderApprovalDTO serviceOrderApprovalDTO) {
        log.info("Approving service order: {}", id);
        var approval = apiMapper.toServiceOrderApproval(serviceOrderApprovalDTO);
        var result = serviceOrderService.approve(id.toString(), approval);
        return ResponseEntity.ok(apiMapper.toServiceOrderApi(result));
    }

    @Override
    public ResponseEntity<ServiceOrderDTO> completeServiceOrder(UUID id) {
        log.info("Completing service order: {}", id);
        var result = serviceOrderService.complete(id.toString());
        return ResponseEntity.ok(apiMapper.toServiceOrderApi(result));
    }

    @Override
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(ServiceOrderRequestDTO serviceOrderRequestDTO) {
        log.info("Creating service order for vehicle: {}", serviceOrderRequestDTO.getVehicleId());
        var internalRequest = apiMapper.toServiceOrderRequest(serviceOrderRequestDTO);
        var result = serviceOrderService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toServiceOrderApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteServiceOrder(UUID id) {
        log.info("Deleting service order: {}", id);
        serviceOrderService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ServiceOrderDTO>> getActiveWarranties(UUID vehicleId) {
        log.info("Fetching active warranties for vehicle: {}", vehicleId);
        var result = serviceOrderService.findActiveWarrantiesByVehicle(vehicleId.toString());
        return ResponseEntity.ok(result.stream().map(apiMapper::toServiceOrderApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ServiceOrderPageDTO> getAllServiceOrders(Integer page, Integer size) {
        log.info("Fetching all service orders");
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = serviceOrderService.findAllPaged(pageable);
        return ResponseEntity.ok(apiMapper.toServiceOrderPageApi(result));
    }

    @Override
    public ResponseEntity<ServiceOrderDTO> getServiceOrderById(UUID id) {
        log.info("Fetching service order: {}", id);
        return serviceOrderService.findById(id.toString())
                .map(so -> ResponseEntity.ok(apiMapper.toServiceOrderApi(so)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ServiceOrderPageDTO> getServiceOrdersByStatus(String status, Integer page, Integer size) {
        log.info("Fetching service orders by status: {}", status);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = serviceOrderService.findByStatus(ServiceOrderStatus.valueOf(status), pageable);
        return ResponseEntity.ok(apiMapper.toServiceOrderPageApi(result));
    }

    @Override
    public ResponseEntity<ServiceOrderPageDTO> getServiceOrdersByVehicle(UUID vehicleId, Integer page, Integer size) {
        log.info("Fetching service orders for vehicle: {}", vehicleId);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = serviceOrderService.findByVehicleId(vehicleId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toServiceOrderPageApi(result));
    }

    @Override
    public ResponseEntity<ServiceOrderDTO> rejectServiceOrder(UUID id, ServiceOrderApprovalDTO serviceOrderApprovalDTO) {
        log.info("Rejecting service order: {}", id);
        var approval = apiMapper.toServiceOrderApproval(serviceOrderApprovalDTO);
        var result = serviceOrderService.reject(id.toString(), approval);
        return ResponseEntity.ok(apiMapper.toServiceOrderApi(result));
    }

    @Override
    public ResponseEntity<Void> updateServiceOrder(UUID id, ServiceOrderRequestDTO serviceOrderRequestDTO) {
        log.info("Updating service order: {}", id);
        var internalRequest = apiMapper.toServiceOrderRequest(serviceOrderRequestDTO);
        serviceOrderService.update(id.toString(), internalRequest);
        return ResponseEntity.ok().build();
    }
}
