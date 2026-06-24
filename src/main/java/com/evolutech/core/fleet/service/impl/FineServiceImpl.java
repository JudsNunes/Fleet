package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.FineMapper;
import com.evolutech.core.fleet.model.dto.request.FineRequestDTO;
import com.evolutech.core.fleet.model.dto.response.FineResponseDTO;
import com.evolutech.core.fleet.model.entity.FineEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.model.utils.enums.FineStatus;
import com.evolutech.core.fleet.repository.FineRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import com.evolutech.core.fleet.service.FineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final VehicleRepository vehicleRepository;
    private final FineMapper fineMapper;

    @Override
    @Transactional
    public Optional<FineResponseDTO> findById(String id) {
        log.info("Finding fine by id: {}", id);
        return fineRepository.findById(id).map(fineMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public FineResponseDTO save(FineRequestDTO body) {
        log.info("Creating fine for vehicle: {}", body.getVehicleId());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        FineEntity entity = fineMapper.toEntity(body, vehicle);
        FineEntity savedEntity = fineRepository.save(entity);
        return fineMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional
    public FineResponseDTO update(String id, FineRequestDTO body) {
        log.info("Updating fine: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        FineEntity existingFine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));

        VehicleEntity vehicle = vehicleRepository.findById(body.getVehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + body.getVehicleId()));

        existingFine.setVehicle(vehicle);
        existingFine.setDriverCpf(body.getDriverCpf());
        existingFine.setDescription(body.getDescription());
        existingFine.setAmount(body.getAmount());
        existingFine.setInfractionDate(body.getInfractionDate());
        existingFine.setPoints(body.getPoints());
        existingFine.setStatus(FineStatus.valueOf(body.getStatus()));
        existingFine.setCostCenterId(body.getCostCenterId());

        FineEntity updatedEntity = fineRepository.save(existingFine);
        return fineMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting fine: {}", id);

        FineEntity fine = fineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fine not found with id: " + id));

        fineRepository.delete(fine);
    }

    @Override
    @Transactional
    public Page<FineResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all fines paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return fineRepository.findAllOrderByInfractionDateDesc(pageable).map(fineMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<FineResponseDTO> findByDriverCpf(String driverCpf, Pageable pageable) {
        log.info("Finding fines by driver CPF: {}", driverCpf);
        return fineRepository.findByDriverCpf(driverCpf, pageable).map(fineMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<FineResponseDTO> findByVehicleId(String vehicleId, Pageable pageable) {
        log.info("Finding fines by vehicle: {}", vehicleId);
        return fineRepository.findByVehicleId(vehicleId, pageable).map(fineMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<FineResponseDTO> findByStatus(FineStatus status, Pageable pageable) {
        log.info("Finding fines by status: {}", status);
        return fineRepository.findByStatus(status, pageable).map(fineMapper::toResponseDTO);
    }
}
