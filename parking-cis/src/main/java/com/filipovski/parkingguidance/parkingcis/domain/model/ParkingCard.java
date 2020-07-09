package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import com.filipovski.parkingguidance.sharedkernel.domain.person.FullName;
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

    @Embedded
    @AttributeOverride(name = "credit", column = @Column(name = "card_credit"))
    private CardCredit cardCredit;

    public ParkingCard() {}

    public ParkingCard(@NonNull FullName fullName, @NonNull CardCredit cardCredit) {
        super(DomainObjectId.randomId(ParkingCardId.class));
        setFullName(fullName);
        setCardCredit(cardCredit);
    }

    public void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, "Must enter a full name");
        this.fullName = fullName;
    }

    private void setCardCredit(CardCredit cardCredit) {
        this.cardCredit = cardCredit;
    }

    public void addCredit(CardCredit cardCredit) {
        Objects.requireNonNull(cardCredit, "CardCredit must not be null!");

        this.cardCredit = cardCredit.addCredit(cardCredit);
    }

    public void removeCredit() {
        this.cardCredit = cardCredit.removeCredit(); // It is allowed to go negative
    }
}
