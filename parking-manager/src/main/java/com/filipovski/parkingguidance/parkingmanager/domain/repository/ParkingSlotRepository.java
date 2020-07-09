package com.filipovski.parkingguidance.parkingmanager.domain.repository;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlipId;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, ParkingSlotId> {
    @Query("select slip.parkingSlot from ParkingSlip slip where slip.id = :parkingSlipId")
    Optional<ParkingSlot> findSlotByParkingSlipId(ParkingSlipId parkingSlipId);
}
