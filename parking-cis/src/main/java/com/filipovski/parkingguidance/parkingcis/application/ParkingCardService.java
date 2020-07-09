package com.filipovski.parkingguidance.parkingcis.application;

import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCardId;
import com.filipovski.parkingguidance.parkingcis.domain.repository.ParkingCardRepository;
import com.filipovski.parkingguidance.parkingcis.integration.ParkingSlipCreatedEvent;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingCardService {
    private final ParkingCardRepository parkingCardRepository;

    public ParkingCardService(ParkingCardRepository parkingCardRepository) {
        this.parkingCardRepository = parkingCardRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onParkingSlipCreated(@NonNull ParkingSlipCreatedEvent event) {
        ParkingCard parkingCard = parkingCardRepository.findById(event.parkingCardId())
                .orElseThrow(RuntimeException::new);
        parkingCard.removeCredit();
        parkingCardRepository.saveAndFlush(parkingCard);
    }

    public Optional<ParkingCard> findById(@NonNull ParkingCardId parkingCardId) {
        return parkingCardRepository.findById(parkingCardId);
    }

    @NonNull
    public List<ParkingCard> findAll() {
        return parkingCardRepository.findAll();
    }
}
