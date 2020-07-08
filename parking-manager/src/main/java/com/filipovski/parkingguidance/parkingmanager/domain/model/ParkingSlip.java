package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Table(name = "parking_slips")
public class ParkingSlip extends AbstractEntity<ParkingSlipId> {

    @Column(name = "enter_time")
    private Instant enterTime;

    @Column(name = "exit_time")
    private Instant exitTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private ParkingSlot parkingSlot;

    @Embedded
    @AttributeOverride(name = "id",column = @Column(name = "parking_card_id", nullable = false))
    private ParkingCardId parkingCardId;

    public ParkingSlip() { }

    public ParkingSlip(@NonNull Instant enterTime,
                       @NonNull ParkingSlot parkingSlot,
                       @NonNull ParkingCardId parkingCardId) {
        super(DomainObjectId.randomId(ParkingSlipId.class));
        setEnterTime(enterTime);
        this.parkingSlot = parkingSlot;
        this.parkingCardId = parkingCardId;
    }

    private void setEnterTime(Instant time) {
        this.enterTime = time;
    }

//    private void setExitTime() {}

    public boolean isSlipFor(ParkingSlot parkingSlot) {
        return true;
    }
}
