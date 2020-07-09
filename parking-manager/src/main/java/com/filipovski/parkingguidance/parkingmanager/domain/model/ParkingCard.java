package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import com.filipovski.parkingguidance.sharedkernel.domain.person.FullName;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ParkingCard {
    private ParkingCardId parkingCardId;

    private FullName fullName;

    private CardCredit cardCredit;

    public ParkingCard(@JsonProperty("id") @NonNull ParkingCardId parkingCardId,
                       @JsonProperty("full_name") @NonNull FullName fullName,
                       @JsonProperty("card_credit") @NonNull CardCredit cardCredit) {
        this.parkingCardId = parkingCardId;
        this.fullName = fullName;
        this.cardCredit = cardCredit;
    }

    public boolean hasCredit() {
        return cardCredit.hasCredit();
    }
}
