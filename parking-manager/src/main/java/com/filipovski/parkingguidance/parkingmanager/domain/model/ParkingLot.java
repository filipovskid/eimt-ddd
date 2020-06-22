package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "parking_lots")
public class ParkingLot extends AbstractEntity<ParkingLotId> {

    @Embedded
    private SlotsCount slotsCount;

    @Enumerated
    private ParkingLotStatus parkingLotStatus;

    public ParkingLot() { }

    public ParkingLot(@NonNull  SlotsCount slotsCount) {
        super(DomainObjectId.randomId(ParkingLotId.class));
        openParkingLot();
        setSlotsCount(slotsCount);
    }

    public void setSlotsCount(@NonNull SlotsCount slotsCount) {
        this.slotsCount = slotsCount;
    }

    public void openParkingLot() {
        this.parkingLotStatus = ParkingLotStatus.OPENED;
    }

    public void closeParkingLot() {
        this.parkingLotStatus = ParkingLotStatus.CLOSED;
    }
}
