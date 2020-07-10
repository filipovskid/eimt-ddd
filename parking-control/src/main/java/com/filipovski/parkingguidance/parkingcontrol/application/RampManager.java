package com.filipovski.parkingguidance.parkingcontrol.application;

import com.filipovski.parkingguidance.parkingcontrol.domain.model.Ramp;
import com.filipovski.parkingguidance.parkingcontrol.domain.repository.RampRepository;
import com.filipovski.parkingguidance.parkingcontrol.integration.ParkingLotAvailableEvent;
import com.filipovski.parkingguidance.parkingcontrol.integration.ParkingLotFullEvent;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@Transactional
public class RampManager {
    private final RampRepository rampRepository;

    public RampManager(RampRepository rampRepository) {
        this.rampRepository = rampRepository;
    }

    @NonNull
    public List<Ramp> findAll() {
        return rampRepository.findAll();
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onParkingLotFull(@NonNull ParkingLotFullEvent event) {

        //  For testing purposes it closes all ramps, otherwise it would check whether the ramp ParkingLotId
        //  matches the one sent in the event.

        rampRepository.findAll().forEach(Ramp::close);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onParkingLotAvailable(@NonNull ParkingLotAvailableEvent event) {

        //  For testing purposes all ramps are opened, otherwise only the ramps belonging to ParkingLot with
        //  ParkingLotId matching to the one contained within the event would be opened.

        rampRepository.findAll().forEach(Ramp::open);
    }
}
