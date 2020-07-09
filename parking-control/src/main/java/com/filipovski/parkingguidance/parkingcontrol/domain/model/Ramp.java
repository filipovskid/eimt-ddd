package com.filipovski.parkingguidance.parkingcontrol.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

@Getter
@Entity
public class Ramp extends AbstractEntity<RampId> {
    @Version
    private Long version;

    @Column(name = "ramp_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RampStatus rampStatus;

    @Embedded
    @AttributeOverride(name = "id",column = @Column(name = "parking_lot_id", nullable = false))
    private ParkingLotId parkingLotId;

    public Ramp() { }

    public Ramp(@NonNull ParkingLotId parkingLotId, @NonNull RampStatus rampStatus) {
        super(DomainObjectId.randomId(RampId.class));
        this.parkingLotId = parkingLotId;
        this.rampStatus = rampStatus;
    }

    public void open() {
        this.rampStatus = RampStatus.OPENED;
    }

    public void close() {
        this.rampStatus = RampStatus.CLOSED;
    }

    public ParkingLotId parkingLotId() {
        return parkingLotId;
    }
}
