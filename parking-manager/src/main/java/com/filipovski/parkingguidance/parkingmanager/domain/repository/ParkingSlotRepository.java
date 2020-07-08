package com.filipovski.parkingguidance.parkingmanager.domain.repository;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlotId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, ParkingSlotId> {
}
