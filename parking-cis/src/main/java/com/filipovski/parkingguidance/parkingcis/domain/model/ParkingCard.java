package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(name = "parking_card")
public class ParkingCard extends AbstractEntity<ParkingCardId>{
    @Version
    private Long version;

    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false))
    })
    private FullName fullName;

    public ParkingCard() {}

    public ParkingCard(@NonNull FullName fullName) {
        super(DomainObjectId.randomId(ParkingCardId.class));
    }

    public void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, "Must enter a full name");
        this.fullName = fullName;
    }

}
