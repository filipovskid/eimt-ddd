package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
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

        this.slotStatus = slotStatus.OCCUPIED;
    }

    public void freeSlot() {
        this.slotStatus = SlotStatus.FREE;
    }

    public void addParkingSlip(@NonNull ParkingSlip parkingSlip) {
        if(!parkingSlip.isSlipFor(this))
            throw new IllegalArgumentException("Can't add a parking slip that of a diffrent slot");

    }
}
