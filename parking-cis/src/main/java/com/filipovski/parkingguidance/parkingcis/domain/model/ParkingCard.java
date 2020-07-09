package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Embedded
    @AttributeOverride(name = "credit", column = @Column(name = "card_credit"))
    private CardCredit cardCredit;

    public ParkingCard() {}

    public ParkingCard(@NonNull FullName fullName) {
        super(DomainObjectId.randomId(ParkingCardId.class));
    }

    public void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, "Must enter a full name");
        this.fullName = fullName;
    }

    public CardCredit removeCredit() {
        return cardCredit.removeCredit(); // It is allowed to go negative
    }
}
