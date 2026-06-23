package com.evolutech.core.fleet.infra;

import com.evolutech.core.fleet.model.entity.MaintenanceAlertEntity;
import com.evolutech.core.fleet.model.entity.MaintenanceEntity;
import com.evolutech.core.fleet.model.entity.VehicleEntity;
import com.evolutech.core.fleet.repository.MaintenanceAlertRepository;
import com.evolutech.core.fleet.repository.MaintenanceRepository;
import com.evolutech.core.fleet.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaintenanceAlertScheduler {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceAlertRepository maintenanceAlertRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void checkMaintenanceAlerts() {
        log.info("Starting maintenance alert check...");

        List<VehicleEntity> vehicles = vehicleRepository.findAll();

        for (VehicleEntity vehicle : vehicles) {
            checkMileageAlert(vehicle);
            checkDateAlert(vehicle);
        }

        log.info("Maintenance alert check completed.");
    }

    private void checkMileageAlert(VehicleEntity vehicle) {
        maintenanceRepository.findByVehicleIdAndMaintenanceStatusNotDeleted(vehicle.getId(), com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus.COMPLETED)
                .stream()
                .filter(m -> m.getNextMileage() != null)
                .forEach(maintenance -> {
                    double currentMileage = vehicle.getMileage() != null ? vehicle.getMileage() : 0.0;
                    double nextMileage = maintenance.getNextMileage();
                    double threshold = nextMileage * 0.9;

                    if (currentMileage >= threshold && currentMileage < nextMileage) {
                        createAlert(vehicle, maintenance, 
                            "Manutenção preventiva recomendada. Quilometragem atual: " + currentMileage + 
                            " km, próxima manutenção: " + nextMileage + " km",
                            "MILEAGE_THRESHOLD");
                    }
                });
    }

    private void checkDateAlert(VehicleEntity vehicle) {
        LocalDate today = LocalDate.now();
        LocalDate alertDate = today.plusDays(30);

        maintenanceRepository.findByVehicleIdAndMaintenanceStatusNotDeleted(vehicle.getId(), com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus.COMPLETED)
                .forEach(maintenance -> {
                    if (maintenance.getMaintenanceDate() != null) {
                        LocalDate nextMaintenanceDate = maintenance.getMaintenanceDate().plusYears(1);
                        if (today.isAfter(nextMaintenanceDate.minusDays(30)) && today.isBefore(nextMaintenanceDate)) {
                            createAlert(vehicle, maintenance,
                                "Manutenção preventiva recomendada. Data próxima: " + nextMaintenanceDate,
                                "DATE_THRESHOLD");
                        }
                    }
                });
    }

    private void createAlert(VehicleEntity vehicle, MaintenanceEntity maintenance, String message, String alertType) {
        boolean alreadyExists = maintenanceAlertRepository.findActiveByVehicleId(vehicle.getId())
                .stream()
                .anyMatch(a -> a.getMaintenance().getId().equals(maintenance.getId()) && 
                              a.getAlertType().equals(alertType));

        if (!alreadyExists) {
            MaintenanceAlertEntity alert = new MaintenanceAlertEntity();
            alert.setVehicle(vehicle);
            alert.setMaintenance(maintenance);
            alert.setMessage(message);
            alert.setIsActive(true);
            alert.setAlertType(alertType);
            maintenanceAlertRepository.save(alert);
            log.info("Created maintenance alert for vehicle {}: {}", vehicle.getPlate(), message);
        }
    }
}
