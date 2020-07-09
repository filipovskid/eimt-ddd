package com.filipovski.parkingguidance.parkingcis.domain.repository;

import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCardId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingCardRepository extends JpaRepository<ParkingCard, ParkingCardId> {
}
