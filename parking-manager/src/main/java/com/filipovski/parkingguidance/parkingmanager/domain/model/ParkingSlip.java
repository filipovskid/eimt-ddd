package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
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

//    @Embedded
//    @AttributeOverride(name = "id",column = @Column(name = "car_id", nullable = false))
//    private CarId cadId;

    public ParkingSlip() { }

    public ParkingSlip(@NonNull Instant enterTime) {
        setEnterTime(enterTime);
    }

    private void setEnterTime(Instant time) {
        this.enterTime = time;
    }

//    private void setExitTime() {}
}
