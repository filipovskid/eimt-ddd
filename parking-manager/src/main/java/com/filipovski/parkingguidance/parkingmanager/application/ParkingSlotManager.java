package com.filipovski.parkingguidance.parkingmanager.application;

import com.filipovski.parkingguidance.parkingmanager.application.dto.ParkingSlotReservationDto;
import com.filipovski.parkingguidance.parkingmanager.domain.event.ParkingSlipCreated;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlip;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.repository.ParkingSlotRepository;
import com.filipovski.parkingguidance.parkingmanager.domain.service.ParkingManager;
import lombok.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ParkingSlotManager {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ParkingSlotRepository parkingSlotRepository;
    private final ParkingManager parkingManager;
//    private final Validator validator;

    public ParkingSlotManager(ApplicationEventPublisher applicationEventPublisher,
                              ParkingSlotRepository parkingSlotRepository,
                              ParkingManager parkingManager) {

        this.applicationEventPublisher = applicationEventPublisher;
        this.parkingSlotRepository = parkingSlotRepository;
        this.parkingManager = parkingManager;
//        this.validator = validator;
    }

    public ParkingSlip reserveParkingSlot(@NonNull ParkingSlotReservationDto slotReservation) {
        Objects.requireNonNull(slotReservation,"Slot reservation must not be null");
//        var constraintViolations = validator.validate(slotReservation);
//
//        if (constraintViolations.size() > 0) {
//            throw new ConstraintViolationException("The slot reservation is not valid", constraintViolations);
//        }

        ParkingSlot parkingSlot = parkingManager.checkParkingSlot(slotReservation.getParkingSlotId());
        parkingSlot.occupySlot();
        ParkingSlip parkingSlip = parkingManager.generateParkingSlip(parkingSlot, slotReservation.getParkingCardId());
        parkingSlotRepository.saveAndFlush(parkingSlot);

        applicationEventPublisher.publishEvent(new ParkingSlipCreated(parkingSlip.getId(), parkingSlip.getEnterTime()));

        return parkingSlip;
    }

    @NonNull
    public List<ParkingSlot> findAll() {
        return parkingSlotRepository.findAll();
    }
}
