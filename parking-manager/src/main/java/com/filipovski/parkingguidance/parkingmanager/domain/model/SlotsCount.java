package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class SlotsCount implements ValueObject {

    @Column(name = "slots_count")
    private int numberOfSlots;

    @Column(name = "occupied_slots_count")
    private int numberOfOccupiedSlots;

    public SlotsCount() { }

    private SlotsCount(int numberOfSlots, int numberOfOccupiedSlots) {
        this.numberOfSlots = numberOfSlots;
        this.numberOfOccupiedSlots = numberOfOccupiedSlots;
    }

    public static SlotsCount of(int numberOfSlots, int numberOfOccupiedSlots) {
        if(numberOfSlots < 1)
            throw new IllegalArgumentException("Parking lot should have at least 1 slot available !");

        if(numberOfSlots < numberOfOccupiedSlots)
            throw new RuntimeException("Number of occupied slots should never exceed number of slots!");

        return new SlotsCount(numberOfSlots, numberOfOccupiedSlots);
    }

    public SlotsCount slotOccupied() {
        if(numberOfOccupiedSlots == numberOfSlots)
            throw new RuntimeException("No slots are available");

        return SlotsCount.of(numberOfSlots, numberOfOccupiedSlots - 1);
    }

    public SlotsCount slotFreed() {
        if(numberOfOccupiedSlots == 0)
            throw new RuntimeException("No slots were occupied");

        return SlotsCount.of(numberOfSlots, numberOfOccupiedSlots - 1);
    }

    @Transient
    public int getNumberOfSlotsAvailable() {
        return this.numberOfSlots - this.numberOfOccupiedSlots;
    }
}
