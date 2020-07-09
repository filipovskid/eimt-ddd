package com.filipovski.parkingguidance.parkingcis;

import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingcis.domain.repository.ParkingCardRepository;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import com.filipovski.parkingguidance.sharedkernel.domain.person.FullName;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {
    private final ParkingCardRepository parkingCardRepository;

    public DataGenerator(ParkingCardRepository parkingCardRepository) {
        this.parkingCardRepository = parkingCardRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        if(parkingCardRepository.findAll().size() == 0) {
            List<ParkingCard> parkingCards = new ArrayList<ParkingCard>();

            parkingCards.add(new ParkingCard(FullName.of("Darko", "Filipovski"), CardCredit.of(5)));
            parkingCards.add(new ParkingCard(FullName.of("Marko", ""), CardCredit.of(6)));
            parkingCards.add(new ParkingCard(FullName.of("Ana", ""), CardCredit.of(10)));
            parkingCards.add(new ParkingCard(FullName.of("Matej", ""), CardCredit.of(3)));
            parkingCards.add(new ParkingCard(FullName.of("Marija", ""), CardCredit.of(2)));

            parkingCardRepository.saveAll(parkingCards);
        }
    }
}