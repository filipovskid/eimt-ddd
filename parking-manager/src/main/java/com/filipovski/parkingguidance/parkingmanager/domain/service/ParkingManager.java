package com.filipovski.parkingguidance.parkingmanager.domain.service;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCardId;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlip;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlotId;
import com.filipovski.parkingguidance.parkingmanager.domain.repository.ParkingSlotRepository;
import lombok.NonNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class ParkingManager {
    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingManager(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public ParkingSlot checkParkingSlot(@NonNull ParkingSlotId parkingSlotId) {
        Objects.requireNonNull(parkingSlotId, "ParkingSlotId is null");

        return parkingSlotRepository.findById(parkingSlotId)
                .orElseThrow(() -> new RuntimeException("ParkingSlot not found"));
    }

    public ParkingSlip generateParkingSlip(@NonNull ParkingSlot parkingSlot,
                                           @NonNull ParkingCardId parkingCardId) {
        ParkingSlip parkingSlip = new ParkingSlip(Instant.now(), parkingSlot, parkingCardId);
        parkingSlot.addParkingSlip(parkingSlip);

        return parkingSlip;
    }
}
