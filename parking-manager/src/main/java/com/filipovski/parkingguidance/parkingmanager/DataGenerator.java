package com.filipovski.parkingguidance.parkingmanager;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingLot;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import com.filipovski.parkingguidance.parkingmanager.domain.model.SlotIdentifier;
import com.filipovski.parkingguidance.parkingmanager.domain.model.SlotsCount;
import com.filipovski.parkingguidance.parkingmanager.domain.repository.ParkingSlotRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {
    private final ParkingSlotRepository parkingSlotRepository;

    public DataGenerator(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        if(parkingSlotRepository.findAll().size() == 0) {
            ParkingLot parkingLot = new ParkingLot(SlotsCount.of(5, 0));
            List<ParkingSlot> parkingSlots = new ArrayList<ParkingSlot>();

            parkingSlots.add(new ParkingSlot(parkingLot, SlotIdentifier.of(1, 1)));
            parkingSlots.add(new ParkingSlot(parkingLot, SlotIdentifier.of(1, 2)));
            parkingSlots.add(new ParkingSlot(parkingLot, SlotIdentifier.of(1, 3)));
            parkingSlots.add(new ParkingSlot(parkingLot, SlotIdentifier.of(2, 1)));
            parkingSlots.add(new ParkingSlot(parkingLot, SlotIdentifier.of(2, 2)));

            parkingSlotRepository.saveAll(parkingSlots);
        }
    }
}
