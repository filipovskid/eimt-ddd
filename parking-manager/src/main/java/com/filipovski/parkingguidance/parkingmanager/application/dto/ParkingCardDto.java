package com.filipovski.parkingguidance.parkingmanager.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCard;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import com.filipovski.parkingguidance.sharedkernel.domain.person.FullName;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ParkingCardDto {
    @JsonProperty("id")
    private String parkingCardId;

    @JsonProperty("full_name")
    private FullName fullName;

    @JsonProperty("card_credit")
    private CardCredit cardCredit;

    public ParkingCardDto(@NonNull String id, @NonNull FullName fullName, @NonNull CardCredit cardCredit) {
        this.parkingCardId = id;
        this.fullName = fullName;
        this.cardCredit = cardCredit;
    }

    public static ParkingCardDto of(@NonNull ParkingCard parkingCard) {
        return new ParkingCardDto(parkingCard.getParkingCardId().getId(),
                parkingCard.getFullName(),
                parkingCard.getCardCredit());
    }
}
