package com.filipovski.parkingguidance.parkingmanager.application;

import com.filipovski.parkingguidance.parkingmanager.application.dto.ParkingSlotReservationDto;
import com.filipovski.parkingguidance.parkingmanager.domain.event.ParkingSlipCreated;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlip;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlipId;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.repository.ParkingSlotRepository;
import com.filipovski.parkingguidance.parkingmanager.domain.service.ParkingManager;
import lombok.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ParkingSlotManager {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingManager parkingManager;
    private final ParkingCardManager parkingCardManager;
//    private final Validator validator;

    public ParkingSlotManager(ApplicationEventPublisher applicationEventPublisher,
                              ParkingSlotRepository parkingSlotRepository,
                              ParkingManager parkingManager,
                              ParkingCardManager parkingCardManager) {

        this.applicationEventPublisher = applicationEventPublisher;
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingManager = parkingManager;
        this.parkingCardManager = parkingCardManager;
    }

    public ParkingSlip reserveParkingSlot(@NonNull ParkingSlotReservationDto slotReservation) {
        Objects.requireNonNull(slotReservation,"Slot reservation must not be null");

        ParkingCard parkingCard = parkingCardManager.findById(slotReservation.getParkingCardId())
                .orElseThrow(() -> new RuntimeException("Parking card not found"));

        // Orchestrate
        ParkingSlot parkingSlot = parkingManager.checkParkingSlot(slotReservation.getParkingSlotId());
        parkingManager.checkCardCredit(parkingCard);
        parkingSlot.occupySlot();
        ParkingSlip parkingSlip = parkingManager.generateParkingSlip(parkingSlot, slotReservation.getParkingCardId());
        parkingSlotRepository.saveAndFlush(parkingSlot);

        applicationEventPublisher.publishEvent(new ParkingSlipCreated(
                parkingSlip.getId(),
                parkingSlip.getParkingCardId(),
                parkingSlot.getParkingLot().getId(),
                parkingSlip.getEnterTime())
        );

        parkingSlot.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        parkingSlot.clearDomainEvents();

        return parkingSlip;
    }

    public void freeParkingSlot(@NonNull ParkingSlipId parkingSlipId) {
        Objects.requireNonNull(parkingSlipId, "ParkingSlipId must not be null");

        ParkingSlot parkingSlot = parkingSlotRepository.findSlotByParkingSlipId(parkingSlipId)
                .orElseThrow(() -> new RuntimeException("ParkingSlot not found"));
        parkingSlot.freeSlot();
        parkingManager.setParkingSlipExitTime(parkingSlot, parkingSlipId);

        parkingSlotRepository.saveAndFlush(parkingSlot);

        parkingSlot.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        parkingSlot.clearDomainEvents();
    }

    @NonNull
    public List<ParkingSlot> findAll() {
        return parkingSlotRepository.findAll();
    }
}
