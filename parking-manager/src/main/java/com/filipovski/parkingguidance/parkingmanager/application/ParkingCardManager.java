package com.filipovski.parkingguidance.parkingmanager.application;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCardId;

import java.util.Optional;

public interface ParkingCardManager {
    Optional<ParkingCard> findById(ParkingCardId parkingCardId);
}
