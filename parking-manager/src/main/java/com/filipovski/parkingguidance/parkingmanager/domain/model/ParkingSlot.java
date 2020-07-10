package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.parkingmanager.domain.event.ParkingLotAvailable;
import com.filipovski.parkingguidance.parkingmanager.domain.event.ParkingLotFull;
import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "parking_slot")
public class ParkingSlot extends AbstractEntity<ParkingSlotId> {
    @Version
    private Long version;

    @AttributeOverride(name = "identifier", column = @Column(name = "slot_number", nullable = false))
    private SlotIdentifier identifier;

    @Column(name = "slot_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private ParkingLot parkingLot;

    @OneToMany(
            mappedBy = "parkingSlot",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ParkingSlip> parkingSlips;

    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public ParkingSlot() {}

    public ParkingSlot(@NonNull  ParkingLot parkingLot, @NonNull SlotIdentifier identifier) {
        super(DomainObjectId.randomId(ParkingSlotId.class));
        setIdentifier(identifier);
        setSlotStatus(SlotStatus.FREE);
        this.parkingLot = parkingLot;
    }

    public boolean isSlotOccupied() {
        return this.slotStatus == SlotStatus.OCCUPIED;
    }

    public void setIdentifier(SlotIdentifier identifier) {
        this.identifier = identifier;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }

    public void occupySlot() {
        if(slotStatus == slotStatus.OCCUPIED)
            throw new RuntimeException("Slot is already occupied");

        slotStatus = slotStatus.OCCUPIED;
        ParkingLotStatus previousLotStatus = parkingLot.getParkingLotStatus();
        parkingLot.slotOccupied();
        ParkingLotStatus currentLotStatus = parkingLot.getParkingLotStatus();

        if(currentLotStatus != previousLotStatus)
            addDomainEvent(new ParkingLotFull(parkingLot.getId(), Instant.now()));
    }

    public void freeSlot() {
        this.slotStatus = SlotStatus.FREE;

        ParkingLotStatus previousLotStatus = parkingLot.getParkingLotStatus();
        parkingLot.slotFreed();
        ParkingLotStatus currentLotStatus = parkingLot.getParkingLotStatus();

        if(currentLotStatus != previousLotStatus)
            addDomainEvent(new ParkingLotAvailable(parkingLot.getId(), Instant.now()));
    }

    public void addParkingSlip(@NonNull ParkingSlip parkingSlip) {
        if(!parkingSlip.isSlipFor(this))
            throw new IllegalArgumentException("Can't add a parking slip that of a diffrent slot");

        this.parkingSlips.add(parkingSlip);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void addDomainEvent(@NonNull DomainEvent event) {
        domainEvents.add(event);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
